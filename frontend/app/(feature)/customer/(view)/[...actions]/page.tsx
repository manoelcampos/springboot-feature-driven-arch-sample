'use client';

import { useCallback, useEffect } from 'react';

import { Textarea, TextInput } from '@mantine/core';
import { useForm, zodResolver } from '@mantine/form';
import { useParams } from 'next/navigation';

import { CustomerService } from '@/app/(feature)/customer/service/CustomerService';
import { CustomerValidationSchema } from '@/app/(feature)/customer/validation/CustomerValidationSchema';
import { CityService } from '@/app/(feature)/city/service/CityService';
import { CustomerDTO } from '@/app/model/models.generated';
import { SelectField } from '@/app/shared/component/molecule/SelectField/SelectField';
import { FormPage } from '@/app/shared/component/template/FormPage';
import { FormType } from '@/app/shared/component/template/FormPage/FormPage';
import useService from '@/app/shared/hook/useService';
import { parseActionsPageParams } from '@/app/shared/util';

function Page() {
    const customerService = new CustomerService();
    const cityService = new CityService();

    const params = useParams();
    const { action, id } = parseActionsPageParams(params.actions);

    const { data, loading: isLoading } = useService(
        useCallback(() => (id ? customerService.getDtoById(id) : Promise.resolve(null)), [id])
    );
    const { data: citiesData, loading: citiesLoading } = useService(useCallback(() => cityService.getAll(), []));

    const form = useForm<CustomerDTO>({
        initialValues: { name: '', cityId: 0, socialSecurityNumber: '' },
        validate: zodResolver(CustomerValidationSchema),
    });

    useEffect(() => {
        data?.id && form.initialize(data);
    }, [data]);

    const isReadOnly = action === FormType.View;

    return (
        <FormPage
            title="Customer"
            service={customerService}
            form={form}
            formType={action}
            isLoading={isLoading || citiesLoading}
        >
            <TextInput type="hidden" {...form.getInputProps('id')} disabled={isReadOnly} />

            <Textarea
                label="Descrição"
                placeholder="Digite a descrição"
                autosize
                minRows={2}
                maxRows={5}
                {...form.getInputProps('name')}
                disabled={isReadOnly}
            />

            <SelectField
                label="City"
                data={citiesData}
                getId={city => city.id}
                getDescription={setor => setor.name}
                searchable
                nothingFoundMessage="City não encontrado..."
                clearable
                {...form.getInputProps('cityId')}
                value={`${form.getInputProps('cityId').value}`}
                disabled={isReadOnly}
            />
        </FormPage>
    );
}

export default Page;

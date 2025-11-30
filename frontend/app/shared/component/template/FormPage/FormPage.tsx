import React, { useState } from 'react';

import { Alert, Box, Button, Grid, Group, LoadingOverlay, Stack, Text } from '@mantine/core';
import { UseFormReturnType } from '@mantine/form';
import { showNotification } from '@mantine/notifications';
import { IconArrowBack, IconDeviceFloppy, IconInfoCircle } from '@tabler/icons-react';
import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';

import { CrudService } from '@/app/core/service/CrudService';
import { BaseModel, DTORecord } from '@/app/model/models.generated';
import { PageHeader, PaperCard } from '@/app/shared/component';
import { APP_NAME } from '@/app/shared/const/environment';
import { formatValues } from '@/app/shared/util';

export enum FormType {
    Create,
    Edit,
    View,
}

interface FormPageProps<T extends BaseModel, D extends DTORecord<T>, S extends CrudService<T, D>> {
    service: S;
    title: string;
    formType: FormType;
    form: UseFormReturnType<any>;
    backRoute?: string;
    isLoading?: boolean;
    isFullWidth?: boolean;
}

function FormPage<T extends BaseModel, D extends DTORecord<T>, S extends CrudService<T, D>>(
    props: React.PropsWithChildren<FormPageProps<T, D, S>>
) {
    const { title, formType, service, form, backRoute, isLoading, isFullWidth, children } = props;

    /**
     * Check if a field present in a Zod validation schema is missing in the form.
     * @param fieldName name of the field to find.
     */
    function isFormFieldMissing(fieldName: string) {
        return !form.getInputNode(fieldName);
    }

    /**
     * Show errors for fields that are not present in the form,
     * but are defined in the Zod validation schema given to the useForm hook that created the form object.
     * Without that function, errors for fields in the schema that are not present in the form
     * are now shown to the user (not even a console error is thrown).
     * Therefore, neither the user nor the developer can understand why the form is not being submitted
     * in such a situation.
     */
    function showErrorsForMissingFormFields() {
        const errors = Object.entries(form.errors)
          .map(([fieldName, errorMsg]) => ({ fieldName, errorMsg }))
          .filter(({ fieldName }) => isFormFieldMissing(fieldName))
          .map(({ errorMsg }) => <li>{errorMsg}</li>);

        const icon = <IconInfoCircle />;
        return (
          errors.length > 0 && (
            <Alert color="red" icon={icon}>
                <ul>{errors}</ul>
            </Alert>
          )
        );
    }

    const { id } = form.values;

    const [loading, setLoading] = useState(false);
    const router = useRouter();

    const pathname = usePathname();
    const effectiveBackRoute = backRoute || `/${pathname.split('/').at(1)}`;

    const canShowSubmitButton = formType === FormType.Create || formType === FormType.Edit;
    const formTypeTitle: string = (() => {
        switch (formType) {
            case FormType.Create:
                return 'Criar';
            case FormType.Edit:
                return 'Atualizar';
            case FormType.View:
                return 'Visualizar';
            default:
                return 'Personalizar';
        }
    })();

    const handleSubmit = async (data: D) => {
        setLoading(true);
        const effectiveData = formatValues(data) as D;
        const save = () => service.save(id, effectiveData);
        try {
            await save();
            showNotification({
                withBorder: true,
                color: 'green',
                title: 'Sucesso',
                message: `Sucesso ao ${formTypeTitle.toLowerCase()} cadastro!`,
            });

            if (effectiveBackRoute) router.push(effectiveBackRoute);
        } catch (e: any) {
            showNotification({
                withBorder: true,
                color: 'red',
                title: `Erro ao ${formTypeTitle.toLowerCase()} cadastro`,
                message: e.message,
            });
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <title>{`${formTypeTitle} ${title} - ${APP_NAME}`}</title>
            <Stack gap="lg">
                <PageHeader
                    title={`${formTypeTitle} ${title}`}
                    breadcrumbItems={[
                        { title, href: effectiveBackRoute },
                        { title: `${formTypeTitle} ${title}`, href: '#' },
                    ]}
                />
                <Grid>
                    <Grid.Col span={{ base: 12, lg: isFullWidth ? 12 : 6 }}>
                        <PaperCard>
                            <Box component="form" onSubmit={form.onSubmit(handleSubmit)} pos="relative">
                                <LoadingOverlay
                                    visible={isLoading}
                                    zIndex={1000}
                                    overlayProps={{ radius: 'sm', blur: 2 }}
                                />

                                <Stack>
                                    <Text size="lg" fw={600}>
                                        {formTypeTitle} {title}
                                    </Text>

                                    {children}
                                    {showErrorsForMissingFormFields()}

                                    <Group>
                                        <Button
                                            leftSection={<IconArrowBack size={16} />}
                                            component={Link}
                                            href={effectiveBackRoute}
                                            variant="default"
                                        >
                                            Voltar
                                        </Button>
                                        {canShowSubmitButton && (
                                            <Button
                                                type="submit"
                                                leftSection={<IconDeviceFloppy size={16} />}
                                                loading={loading}
                                            >
                                                {formTypeTitle}
                                            </Button>
                                        )}
                                    </Group>
                                </Stack>
                            </Box>
                        </PaperCard>
                    </Grid.Col>
                </Grid>
            </Stack>
        </>
    );
}

export default FormPage;

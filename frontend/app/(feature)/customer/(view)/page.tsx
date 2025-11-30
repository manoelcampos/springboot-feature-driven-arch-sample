'use client';

import { DataTableProps } from 'mantine-datatable';

import { CustomerService } from '@/app/(feature)/customer/service/CustomerService';
import { Customer } from '@/app/model/models.generated';
import { ListPage } from '@/app/shared/component';

function Page() {
    const service = new CustomerService();
    const title = 'Customer';
    const resourceBasePath = '/customer';

    const columns: DataTableProps<Customer>['columns'] = [
        {
            title: 'Name',
            accessor: 'name',
            sortable: true,
            render: item => <span>{item.name}</span>,
        },
        {
            title: 'City',
            accessor: 'city.name',
            render: item => (
                <span>
                    {item.city?.name} - {item.city?.state.abbreviation}
                </span>
            ),
        },
        {
            title: 'Social Security Number',
            accessor: 'socialSecurityNumber',
            render: item => <span>{item.socialSecurityNumber}</span>,
        },
    ];

    return (
        <ListPage
            title={title}
            titlePlural="Actions"
            breadcrumbItems={[{ title, href: '#' }]}
            addActionRoute={`${resourceBasePath}/create`}
            service={service}
            tableColumns={columns}
            resourceBasePath={resourceBasePath}
        />
    );
}

export default Page;

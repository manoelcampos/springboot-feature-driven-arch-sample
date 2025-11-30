'use client';

import { DataTableProps } from 'mantine-datatable';

import { ListPage } from '@/app/shared/component';
import { City } from '@/app/model/models.generated';
import { CityService } from '@/app/(feature)/city/service/CityService';

function Page() {
    const service = new CityService();
    const title = 'City';
    const resourceBasePath = '/city';

    const columns: DataTableProps<City>['columns'] = [
        {
            title: 'Name',
            accessor: 'name',
            sortable: true,
            render: (item: City) => <span>{item.name}</span>,
        },
        {
            title: 'State',
            accessor: 'state.name',
            render: (item: City) => <span>{item.state.name}</span>,
        },
    ];

    return (
        <ListPage
            title={title}
            titlePlural="Actions"
            breadcrumbItems={[{ title, href: '#' }]}
            addActionRoute={`${resourceBasePath}/criar`}
            service={service}
            tableColumns={columns}
            resourceBasePath={resourceBasePath}
        />
    );
}

export default Page;

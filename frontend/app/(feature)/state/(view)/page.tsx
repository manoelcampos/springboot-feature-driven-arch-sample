'use client';

import { DataTableProps } from 'mantine-datatable';

import { State } from '@/app/model/models.generated';
import { ListPage } from '@/app/shared/component';
import { StateService } from '@/app/(feature)/state/service/StateService';

function Page() {
    const service = new StateService();
    const title = 'State';
    const resourceBasePath = '/state';

    const columns: DataTableProps<State>['columns'] = [
        {
            title: 'Name',
            accessor: 'name',
            sortable: true,
            render: item => <span>{item.name}</span>,
        },
        {
            title: 'City',
            accessor: 'abbreviation',
            render: item => <span>{item.abbreviation}</span>
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

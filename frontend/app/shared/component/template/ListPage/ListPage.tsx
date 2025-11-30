import React, { ReactNode } from 'react';

import { Group, Stack, Text } from '@mantine/core';
import { DataTableProps } from 'mantine-datatable';

import { BaseService } from '@/app/core/service/BaseService';
import { BaseModel } from '@/app/model/models.generated';
import { PageHeader, PaperCard, Table } from '@/app/shared/component';
import { BreadcrumbItem } from '@/app/shared/component/molecule/Breadcrumb/Breadcrumb';
import { ActionsSettings } from '@/app/shared/component/organism/Table/Table';
import { APP_NAME } from '@/app/shared/const/environment';

interface ListPageProps<T, S> {
    title: string;
    titlePlural: string;
    breadcrumbItems: BreadcrumbItem[];
    addActionRoute: string;
    service: S;
    buttonAddIsDisabled?: boolean;
    tableColumns: DataTableProps<T>['columns'];
    resourceBasePath: string;
    actionsSettings?: Partial<ActionsSettings>;
    children?: ReactNode;
    customFetchMethod?: () => Promise<T[]>;
}

function ListPage<M extends BaseModel, S extends BaseService<M>>(props: ListPageProps<M, S>) {
    const {
        title,
        titlePlural,
        breadcrumbItems,
        addActionRoute,
        service,
        buttonAddIsDisabled,
        tableColumns,
        resourceBasePath,
        actionsSettings,
        children,
        customFetchMethod,
    } = props;

    return (
        <>
            <>
                <title>{`${title} - ${APP_NAME}`}</title>
            </>
            <Stack gap="lg">
                <PageHeader
                    title={title}
                    breadcrumbItems={breadcrumbItems}
                    addAction
                    addActionRoute={addActionRoute}
                    buttonAddIsDisabled={buttonAddIsDisabled}
                />
                {children && <Stack>{children}</Stack>}
                <PaperCard>
                    <Group justify="space-between" mb="md">
                        <Text fz="lg" fw={600}>
                            Lista de {titlePlural}
                        </Text>
                    </Group>
                    <Table<M, S>
                        service={service}
                        basePath={resourceBasePath}
                        columns={tableColumns}
                        customFetchMethod={customFetchMethod}
                        actionsSettings={actionsSettings}
                    />
                </PaperCard>
            </Stack>
        </>
    );
}

export default ListPage;

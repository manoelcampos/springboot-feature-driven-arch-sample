'use client';

import React, { useCallback, useEffect, useState } from 'react';

import { ActionIcon, Box, Button, Group, Text, Tooltip } from '@mantine/core';
import { closeAllModals, openModal } from '@mantine/modals';
import { showNotification } from '@mantine/notifications';
import { IconEdit, IconEye, IconTrash } from '@tabler/icons-react';
import { DataTableProps } from 'mantine-datatable';
import Link from 'next/link';

import ViewTable from '../ViewTable/ViewTable';
import { BaseService } from '@/app/core/service/BaseService';
import { BaseModel } from '@/app/model/models.generated';
import { ErrorAlert } from '@/app/shared/component';
import useService from '@/app/shared/hook/useService';

export type ActionsSettings = {
    /**
     * Indicates whether to show the actions column or not.
     */
    canShowActions: boolean;

    /**
     * Indicates whether to show the view action or not.
     */
    canView: boolean;

    /**
     * Indicates whether to show the edit action or not.
     */
    canEdit: boolean;

    /**
     * Indicates whether to show the delete action or not.
     */
    canDelete: boolean;
};

type TableProps<M extends BaseModel, S extends BaseService<M>> = {
    service: S;
    customFetchMethod?: () => Promise<M[]>;
    columns: DataTableProps<M>['columns'];
    basePath: string;
    actionsSettings?: Partial<ActionsSettings>;
    pagination?: boolean;

    /**
     * Custom function to be called when the edit action is clicked.
     * If a function is not provided, the default edit action is execute.
     * @param itemId the id of the item to be edited.
     */
    customEditFunction?: (itemId: number | undefined | null) => void;
};

/**
 * TableProps is a generic type for the ViewTable component props.
 * @template M - Model that extends from {@link BaseModel}
 * @template S - Service that extends from {@link ModelService}
 * @param {S} service - The service to be used by this ViewTable component
 * @param {DataTableProps<M>['columns']} columns - The columns to be displayed in this ViewTable
 * @param {string} basePath - The base path for endpoints to perform CRUD operations (such as delete)
 * @param {(() => Promise<M[]>)} [customFetchMethod] - Optional custom fetch method to get the data to display.
 *        If none is provided, it will use () => service.getAll().
 *        If you want to use an already avaliable data instead of calling some service method,
 *        you could use () => Promise.resolve(data).
 * @param customEditFunction custom function to be called when the edit action is clicked. Check {@link TableProps} for more details.
 * @param {ActionsSettings} [actionsSettings] - Optional settings for actions (such as show, edit, delete).
 *        This object should have the properties:
 *        - canShowActions: boolean - Indicates whether to show the actions column or not.
 *        - canShow: boolean - Indicates whether to show actions or not.
 *        - canEdit: boolean - Indicates whether to allow editing actions or not.
 *        - canDelete: boolean - Indicates whether to allow deletion actions or not.
 * @param pagination - Indicates whether to show pagination or not.
 */
const Table = <M extends BaseModel, S extends BaseService<M>>({
    service,
    columns,
    basePath,
    customFetchMethod,
    customEditFunction,
    actionsSettings,
    pagination = true,
}: TableProps<M, S>) => {
    const fetchMethod = useCallback(
        () => (customFetchMethod ? customFetchMethod() : service.getAll()),
        [customFetchMethod, service]
    );

    const { data: dataService, loading, error } = useService(fetchMethod);
    const [data, setData] = useState<M[]>([]);

    useEffect(() => setData(dataService), [dataService]);

    const deleteRecord = useCallback(
        (item: M) => {
            const id = item?.id ?? 0;

            /*Se não tem um id, o elemento foi inserido apenas no array,
         mas não foi enviado ao backend ainda. Então apenas remove do array. */
            if (!id) {
                setData(data.filter(current => current !== item));
                return;
            }

            service
                .delete(`${id}`)
                .then(() => {
                    showNotification({
                        withBorder: true,
                        color: 'green',
                        title: 'Sucesso',
                        message: 'Cadastro excluído com sucesso!',
                    });

                    fetchMethod().then(newData => setData(newData));
                })
                .catch(e => {
                    showNotification({
                        withBorder: true,
                        color: 'red',
                        title: 'Erro',
                        message: e.message,
                    });
                });
        },
        [service, fetchMethod]
    );

    const defaultActionsSettings: ActionsSettings = {
        canShowActions: true,
        canView: true,
        canEdit: true,
        canDelete: true,
        ...actionsSettings,
    };

    const effectiveColumns: DataTableProps<M>['columns'] = [...(columns ?? [])];

    if (defaultActionsSettings.canShowActions) {
        effectiveColumns.push({
            accessor: 'actions',
            title: <Box mr={6}>Ações</Box>,
            textAlign: 'right',
            render: item => (
                <Group gap={4} justify="right" wrap="nowrap">
                    {defaultActionsSettings.canView && (
                        <Tooltip label="Visualizar">
                            <ActionIcon
                                size="sm"
                                variant="subtle"
                                color="green"
                                component={Link}
                                href={`${basePath}/${item.id}/visualizar`}
                            >
                                <IconEye size={16} />
                            </ActionIcon>
                        </Tooltip>
                    )}
                    {item.id ? (
                        defaultActionsSettings.canEdit && customEditFunction ? (
                            <Tooltip label="Editar">
                                <ActionIcon
                                    size="sm"
                                    variant="subtle"
                                    color="blue"
                                    onClick={() => customEditFunction(item?.id)}
                                >
                                    <IconEdit size={16} />
                                </ActionIcon>
                            </Tooltip>
                        ) : (
                            <Tooltip label="Editar">
                                <ActionIcon
                                    size="sm"
                                    variant="subtle"
                                    color="blue"
                                    component={Link}
                                    href={`${basePath}/${item.id}/editar`}
                                >
                                    <IconEdit size={16} />
                                </ActionIcon>
                            </Tooltip>
                        )
                    ) : null}
                    {defaultActionsSettings.canDelete && (
                        <Tooltip label="Deletar">
                            <ActionIcon
                                size="sm"
                                variant="subtle"
                                color="red"
                                onClick={e => {
                                    e.stopPropagation();
                                    openModal({
                                        title: 'Atenção',
                                        children: (
                                            <>
                                                <Text>
                                                    Você está prestes a excluir este cadastro. Tem certeza de que deseja
                                                    continuar?
                                                </Text>

                                                <Group mt="md" gap="sm" justify="flex-end">
                                                    <Button
                                                        variant="transparent"
                                                        c="dimmed"
                                                        onClick={() => closeAllModals()}
                                                    >
                                                        Cancelar
                                                    </Button>
                                                    <Button
                                                        color="red"
                                                        onClick={() => {
                                                            deleteRecord(item);
                                                            closeAllModals();
                                                        }}
                                                    >
                                                        Confirmar
                                                    </Button>
                                                </Group>
                                            </>
                                        ),
                                    });
                                }}
                            >
                                <IconTrash size={16} />
                            </ActionIcon>
                        </Tooltip>
                    )}
                </Group>
            ),
        });
    }

    if (error) {
        return <ErrorAlert title="Erro ao carregar dados" message={error.toString()} />;
    }

    return <ViewTable data={data} columns={effectiveColumns} loading={loading} pagination={pagination} />;
};

export default Table;

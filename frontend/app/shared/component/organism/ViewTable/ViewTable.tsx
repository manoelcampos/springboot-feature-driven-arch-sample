'use client';

import { useEffect, useState } from 'react';

import { useDebouncedValue } from '@mantine/hooks';
import sortBy from 'lodash/sortBy';
import { DataTable, DataTableProps, DataTableSortStatus } from 'mantine-datatable';

import { BaseModel } from '@/app/model/models.generated';

const PAGE_SIZES = [5, 10, 20];

type TableProps<M extends BaseModel> = {
    data: M[];
    columns: DataTableProps<M>['columns'];
    loading?: boolean;
    pagination?: boolean;
};

/**
 * TableProps is a generic type for the ViewTable component props.
 * @template M - Model that extends from {@link BaseModel}
 * @param data
 * @param {DataTableProps<M>['columns']} columns - The columns to be displayed in this ViewTable
 */
const ViewTable = <M extends BaseModel>({ data, columns, loading, pagination = true }: TableProps<M>) => {
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(PAGE_SIZES[0]);
    const [records, setRecords] = useState<M[]>(data.slice(0, pageSize));
    const [sortStatus, setSortStatus] = useState<DataTableSortStatus>({
        columnAccessor: 'id',
        direction: 'asc',
    });
    const [query] = useState('');
    const [debouncedQuery] = useDebouncedValue(query, 200);
    const recordsWithKeys = records.map((record, index) => ({
        ...record,
        key: index.toString(), // Definindo a chave como o índice da linha convertido para string
    }));

    const effectiveColumns: DataTableProps<M>['columns'] = [
        {
            title: 'ID',
            accessor: 'id',
            sortable: true,
            render: item => <span>{item.id}</span>,
        },
        // @ts-ignore
        ...columns,
    ];

    useEffect(() => {
        setPage(1);
    }, [pageSize]);

    useEffect(() => {
        const totalPages = Math.ceil(data.length / pageSize);
        if (totalPages && page > totalPages) {
            setPage(totalPages);
        }
    }, [data]);

    useEffect(() => {
        const from = (page - 1) * pageSize;
        const to = from + pageSize;
        const d = sortBy(data, sortStatus.columnAccessor) as M[];
        const dd = d.slice(from, to) as M[];
        const filtered = sortStatus.direction === 'desc' ? dd.reverse() : dd;

        setRecords(filtered);
    }, [sortStatus, data, page, pageSize, debouncedQuery]);

    const paginationProps: Partial<DataTableProps> = pagination
        ? {
              page,
              onPageChange: p => setPage(p),
              totalRecords: data.length,
              recordsPerPage: pageSize,
              recordsPerPageOptions: PAGE_SIZES,
              onRecordsPerPageChange: setPageSize,
              recordsPerPageLabel: 'Registros por página',
              paginationText: ({ from, to, totalRecords }) => `Exibindo ${from} a ${to} de ${totalRecords} registros`,
          }
        : {};

    return (
        <DataTable
            minHeight={200}
            verticalSpacing="sm"
            striped
            // @ts-ignore
            columns={effectiveColumns}
            // @ts-ignore
            records={recordsWithKeys}
            noRecordsText="Não há registros disponíveis para exibição."
            fetching={loading}
            sortStatus={sortStatus}
            onSortStatusChange={setSortStatus}
            {...paginationProps}
        />
    );
};

export default ViewTable;

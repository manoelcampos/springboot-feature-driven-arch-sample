'use client';

import { PaperProps, Stack } from '@mantine/core';

import { PageHeader, StatsGrid } from '@/app/shared/component';
import { APP_DESCRIPTION, APP_NAME } from '@/app/shared/const/environment';
import { useFetchData } from '@/app/shared/hook';

const PAPER_PROPS: PaperProps = {
    p: 'md',
    radius: 'md',
    style: { height: '100%' },
    withBorder: true,
};

function Page() {
    const { data: statsData, error: statsError, loading: statsLoading } = useFetchData('/mocks/StatsGrid.json');

    return (
        <>
            <>
                <title>{`Início - ${APP_NAME}`}</title>
                <meta name="description" content={APP_DESCRIPTION} />
            </>
            <Stack gap="lg">
                <PageHeader title="Início" withActions />
                <StatsGrid data={statsData.data} loading={statsLoading} error={statsError} paperProps={PAPER_PROPS} />
            </Stack>
        </>
    );
}

export default Page;

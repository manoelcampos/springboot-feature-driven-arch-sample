'use client';

import * as React from 'react';

import { MantineProvider } from '@mantine/core';
import { ModalsProvider } from '@mantine/modals';
import { Notifications } from '@mantine/notifications';
import NextTopLoader from 'nextjs-toploader';

import { theme } from '@/app/core/config/theme';
import { Layout } from '@/app/shared/component/template/Layout/Layout';

export interface ProvidersProps {
    children: React.ReactNode;
}

export function Providers({ children }: ProvidersProps) {
    return (
        <MantineProvider theme={theme} defaultColorScheme="auto">
            <NextTopLoader />
            <Notifications position="bottom-right" zIndex={1000} />
            <ModalsProvider>
                <Layout>{children}</Layout>
            </ModalsProvider>
        </MantineProvider>
    );
}

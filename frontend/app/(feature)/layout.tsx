'use client';

import React from 'react';

import '@mantine/carousel/styles.css';
import { ColorSchemeScript } from '@mantine/core';
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/notifications/styles.css';
import '@mantine/tiptap/styles.css';
import 'mantine-datatable/styles.layer.css';
import { Open_Sans } from 'next/font/google';
import { APP_DESCRIPTION } from '@/app/shared/const/environment';

import { Providers } from '@/app/core/config/providers';

const openSans = Open_Sans({
    subsets: ['latin'],
    display: 'swap',
});
export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="pt" className={openSans.className}>
            <head>
                <title>{APP_DESCRIPTION}</title>
                <link rel="shortcut icon" href="/favicon.svg" />
                <meta name="viewport" content="minimum-scale=1, initial-scale=1, width=device-width" />
                <meta
                    name="description"
                    content={APP_DESCRIPTION}
                />
                <ColorSchemeScript defaultColorScheme="auto" />
            </head>
            <body>
                <Providers>{children}</Providers>
            </body>
        </html>
    );
}

'use client';

import { createTheme, PaperProps } from '@mantine/core';

export const theme = createTheme({
    fontFamily: 'Open Sans, sans-serif',
    primaryColor: 'matisse',
    colors: {
        matisse: [
            '#f0f8ff',
            '#dff0ff',
            '#b9e1fe',
            '#7bcafe',
            '#34affc',
            '#0a96ed',
            '#0076cb',
            '#0065b1',
            '#055087',
            '#0a4270',
        ],
    },
});

export const PAPER_PROPS: PaperProps = {
    p: 'md',
    radius: 'md',
    style: { height: '100%' },
    withBorder: true,
};

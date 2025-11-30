'use client';

import React, { ReactNode } from 'react';

import { Paper, PaperProps } from '@mantine/core';

type PaperCardProps = { children: ReactNode };

const PAPER_PROPS: PaperProps = {
    p: 'md',
    radius: 'md',
    withBorder: true,
};

const PaperCard = ({ children }: PaperCardProps) => <Paper {...PAPER_PROPS}>{children}</Paper>;

export default PaperCard;

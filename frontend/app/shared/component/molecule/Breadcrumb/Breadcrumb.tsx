import React from 'react';

import { Anchor, Breadcrumbs, BreadcrumbsProps, rem, Text, useMantineTheme } from '@mantine/core';
import { useColorScheme } from '@mantine/hooks';
import Link from 'next/link';

export interface BreadcrumbItem {
    title: string;
    href: string;
}

export interface BreadcrumbProps {
    items: BreadcrumbItem[];
}

const Breadcrumb: React.FC<BreadcrumbProps> = ({ items }) => {
    const theme = useMantineTheme();
    const colorScheme = useColorScheme();
    const BREADCRUMBS_PROPS: Omit<BreadcrumbsProps, 'children'> = {
        style: {
            a: {
                padding: rem(8),
                borderRadius: theme.radius.sm,
                fontWeight: 500,
                color: colorScheme === 'dark' ? theme.white : theme.black,

                '&:hover': {
                    transition: 'all ease 150ms',
                    backgroundColor: colorScheme === 'dark' ? theme.colors.dark[5] : theme.colors.gray[2],
                    textDecoration: 'none',
                },
            },
        },
    };

    return (
        <>
            <Breadcrumbs {...BREADCRUMBS_PROPS}>
                {items.map((item, index) =>
                    item.href === '#' ? (
                        <Text c="dimmed" key={index}>
                            {item.title}
                        </Text>
                    ) : (
                        <Anchor component={Link} href={item.href} key={index}>
                            {item.title}
                        </Anchor>
                    )
                )}
            </Breadcrumbs>
        </>
    );
};

export default Breadcrumb;

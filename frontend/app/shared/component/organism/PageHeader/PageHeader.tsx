'use client';

import { ActionIcon, Button, Divider, Flex, Paper, PaperProps, Stack, Text, Title } from '@mantine/core';
import { IconPlus, IconRefresh } from '@tabler/icons-react';
import Link from 'next/link';

import { FilterDateMenu, Surface } from '@/app/shared/component';
import Breadcrumb, { BreadcrumbItem } from '@/app/shared/component/molecule/Breadcrumb/Breadcrumb';

type PageHeaderProps = {
    title: string;
    withActions?: boolean;
    breadcrumbItems?: BreadcrumbItem[];
    addAction?: boolean;
    buttonAddIsDisabled?: boolean;
    addActionRoute?: string;
} & PaperProps;

const PageHeader = (props: PageHeaderProps) => {
    const { withActions, breadcrumbItems, title, addAction, buttonAddIsDisabled, addActionRoute, ...others } = props;

    return (
        <>
            <Surface component={Paper} style={{ backgroundColor: 'transparent' }} {...others}>
                {withActions ? (
                    <Flex justify="space-between" direction={{ base: 'column', sm: 'row' }} gap={{ base: 'sm', sm: 4 }}>
                        <Stack gap={4}>
                            <Title order={3}>{title}</Title>
                            <Text>Olá, José da Silva!</Text>
                        </Stack>
                        <Flex align="center" gap="sm">
                            <ActionIcon variant="subtle">
                                <IconRefresh size={16} />
                            </ActionIcon>
                            <FilterDateMenu />
                        </Flex>
                    </Flex>
                ) : addAction ? (
                    <Flex
                        align="center"
                        justify="space-between"
                        direction={{ base: 'row', sm: 'row' }}
                        gap={{ base: 'sm', sm: 4 }}
                    >
                        <Stack>
                            <Title order={3}>{title}</Title>
                            <Breadcrumb items={[{ title: 'Início', href: '/' }, ...(breadcrumbItems ?? [])]} />
                        </Stack>
                        {!buttonAddIsDisabled ? (
                            <Button leftSection={<IconPlus size={18} />} component={Link} href={addActionRoute ?? '/'}>
                                Adicionar
                            </Button>
                        ) : (
                            ''
                        )}
                    </Flex>
                ) : (
                    <Stack gap="sm">
                        <Title order={3}>{title}</Title>
                        <Breadcrumb items={breadcrumbItems ?? []} />
                    </Stack>
                )}
            </Surface>
            <Divider />
        </>
    );
};

export default PageHeader;

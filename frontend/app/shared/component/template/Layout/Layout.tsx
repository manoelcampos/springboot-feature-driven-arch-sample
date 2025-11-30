import React from 'react';

import {
    ActionIcon,
    AppShell,
    Avatar,
    Burger,
    Container,
    Flex,
    Group,
    Menu,
    Title,
    Tooltip,
    UnstyledButton,
    useComputedColorScheme,
    useMantineColorScheme,
} from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import { IconLogout, IconMoon, IconSettings, IconSun, IconUser } from '@tabler/icons-react';
import cx from 'clsx';

import classes from './Demo.module.css';
import { AppMain, Navbar } from '@/app/shared/component';
import { APP_DESCRIPTION } from '@/app/shared/const/environment';

interface Props {
    children: React.ReactNode;
}

export const Layout = ({ children }: Props) => {
    const [opened, { toggle }] = useDisclosure();

    const { setColorScheme } = useMantineColorScheme();
    const computedColorScheme = useComputedColorScheme('light', { getInitialValueInEffect: true });

    return (
        <AppShell
            header={{ height: { base: 50, lg: 60 } }}
            navbar={{
                width: { base: 300 },
                breakpoint: 'sm',
                collapsed: { mobile: !opened },
            }}
            padding={0}
        >
            <AppShell.Header>
                <Flex h="100%" gap="md" justify="space-between" align="center" direction="row" wrap="wrap" px="md">
                    <Group h="100%">
                        <Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm" />

                        <Title order={5} pt="0.5rem">
                            {APP_DESCRIPTION}
                        </Title>
                    </Group>

                    <Group>
                        <ActionIcon
                            onClick={() => setColorScheme(computedColorScheme === 'light' ? 'dark' : 'light')}
                            variant="default"
                            size="xl"
                            aria-label="Toggle color scheme"
                        >
                            <IconSun className={cx(classes.icon, classes.light)} stroke={1.5} />
                            <IconMoon className={cx(classes.icon, classes.dark)} stroke={1.5} />
                        </ActionIcon>

                        <Menu shadow="lg" width={200}>
                            <Menu.Target>
                                <Tooltip label="Acessar opções do usuário">
                                    <UnstyledButton>
                                        <Avatar color="matisse" radius="xl" />
                                    </UnstyledButton>
                                </Tooltip>
                            </Menu.Target>
                            <Menu.Dropdown>
                                <Menu.Label tt="uppercase" ta="center" fw={600}>
                                    Opções do Usuário
                                </Menu.Label>
                                <Menu.Item leftSection={<IconUser size={16} />}>Perfil</Menu.Item>
                                <Menu.Item leftSection={<IconSettings size={16} />}>Configurações</Menu.Item>
                                <Menu.Item leftSection={<IconLogout size={16} />}>Sair</Menu.Item>
                            </Menu.Dropdown>
                        </Menu>
                    </Group>
                </Flex>
            </AppShell.Header>

            <Navbar />

            <AppShell.Main>
                <AppMain>
                    <Container p="md" fluid>
                        {children}
                    </Container>
                </AppMain>
            </AppShell.Main>
        </AppShell>
    );
};

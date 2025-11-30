import { Button, Menu } from '@mantine/core';
import { IconChevronDown } from '@tabler/icons-react';

const FilterDateMenu = () => (
    <Menu shadow="md" width={120}>
        <Menu.Target>
            <Button variant="subtle" rightSection={<IconChevronDown size={14} />}>
                Hoje: Jan 24
            </Button>
        </Menu.Target>

        <Menu.Dropdown>
            <Menu.Item>Hoje</Menu.Item>
            <Menu.Item>Ontem</Menu.Item>
            <Menu.Item>Nos últimos 7 dias</Menu.Item>
            <Menu.Item>Nos últimos 40 dias</Menu.Item>
            <Menu.Item>Nesse mês</Menu.Item>
            <Menu.Item>No último mês</Menu.Item>
        </Menu.Dropdown>
    </Menu>
);

export default FilterDateMenu;

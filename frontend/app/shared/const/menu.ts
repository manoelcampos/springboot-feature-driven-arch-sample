import { IconActivity, IconChartDonut2, IconHome, IconProps, IconTargetArrow } from '@tabler/icons-react';

import type { ComponentType } from 'react';

export interface MenuProps {
    link: string;
    label: string;
    icon: ComponentType<IconProps>;
}

export const menuData: MenuProps[] = [
    { link: '/', label: 'Start', icon: IconHome },
    { link: '/city', label: 'Cities', icon: IconChartDonut2 },
    { link: '/state', label: 'Country States', icon: IconTargetArrow },
    { link: '/customer', label: 'Customers', icon: IconActivity },
];

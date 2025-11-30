import React from 'react';

import { AppShell, NavLink } from '@mantine/core';
import { IconLogout } from '@tabler/icons-react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

import classes from '@/app/shared/component/organism/Navbar/Navbar.module.css';
import { menuData } from '@/app/shared/const/menu';

export function Navbar() {
    const pathname = usePathname();
    const links = menuData.map((item, index) => (
        <NavLink
            component={Link}
            href={item.link}
            key={index}
            active={item.link === pathname}
            label={item.label}
            leftSection={<item.icon stroke={1.5} />}
            className={classes.link}
        />
    ));

    return (
        <AppShell.Navbar p="md" className={classes.navbar}>
            <div className={classes.navbarMain}>
                {links.map((link, index) => (
                    <div key={index}>{link}</div>
                ))}
            </div>

            <div className={classes.footer}>
                <NavLink
                    href="#"
                    label="Sair"
                    leftSection={<IconLogout stroke={1.5} />}
                    onClick={event => event.preventDefault()}
                    className={classes.link}
                />
            </div>
        </AppShell.Navbar>
    );
}

export default Navbar;

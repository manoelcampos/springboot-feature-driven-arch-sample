import { ReactNode } from 'react';

import { Box } from '@mantine/core';

import classes from './App.module.css';

type AppMainProps = {
    children: ReactNode;
};

const AppMain = ({ children }: AppMainProps) => (
    <Box p={0} className={classes.main}>
        {children}
    </Box>
);

export default AppMain;

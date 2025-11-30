import React from 'react';

import { Box, Button, Grid, Stack, Text } from '@mantine/core';
import { IconArrowBack } from '@tabler/icons-react';
import Link from 'next/link';

import { PageHeader, PaperCard } from '@/app/shared/component';
import { BreadcrumbItem } from '@/app/shared/component/molecule/Breadcrumb/Breadcrumb';
import { APP_NAME } from '@/app/shared/const/environment';

interface ViewPageProps {
    title: string;
    breadcrumbItems: BreadcrumbItem[];
    backRoute: string;
}

function ViewPage(props: React.PropsWithChildren<ViewPageProps>) {
    const { title, breadcrumbItems, backRoute, children } = props;

    return (
        <>
            <>
                <title>{`Visualizar ${title} - ${APP_NAME}`}</title>
            </>
            <Stack gap="lg">
                <PageHeader
                    title={`Visualizar ${title}`}
                    breadcrumbItems={[...breadcrumbItems, { title: `Visualizar ${title}`, href: '#' }]}
                />
                <Grid>
                    <Grid.Col span={{ base: 12, lg: 12 }}>
                        <PaperCard>
                            <Stack>
                                <Text size="lg" fw={600}>
                                    Visualizar {title}
                                </Text>

                                {children}

                                <Box>
                                    <Button leftSection={<IconArrowBack size={16} />} component={Link} href={backRoute}>
                                        Voltar
                                    </Button>
                                </Box>
                            </Stack>
                        </PaperCard>
                    </Grid.Col>
                </Grid>
            </Stack>
        </>
    );
}

export default ViewPage;

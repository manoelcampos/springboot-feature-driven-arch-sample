import { useCallback, useEffect, useState } from 'react';

const useService = <T>(fetchMethod: () => Promise<T>) => {
    const [data, setData] = useState<T>([] as T);
    const [error, setError] = useState<any>(null);
    const [loading, setLoading] = useState(true);

    const fetchData = useCallback(async () => {
        try {
            const responseData = await fetchMethod();
            setData(responseData);
        } catch (e) {
            setError(e);
        } finally {
            setLoading(false);
        }
    }, [fetchMethod]);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    return { data, error, loading };
};

export default useService;

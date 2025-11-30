// Mesmo usando ES6 modules no projeto, o ESLint 8.21+ não suporta a extensão .mjs
// https://www.raulmelo.me/en/blog/migration-eslint-to-flat-config

import pluginNext from '@next/eslint-plugin-next';
import tsEslint from "typescript-eslint";
import pluginReact from "eslint-plugin-react";
import parser from '@typescript-eslint/parser';
import hooksPlugin from 'eslint-plugin-react-hooks';

/** @type {import('eslint').Linter.FlatConfig[]} */
export default [
    // Global ignores (must be the first item)
    {
        ignores: ["**/models.ts", "dist/**", "node_modules/**", "bin/**", "build/**"],
    },

    {
        name: 'ESLint Config - Next.js',
        languageOptions: {
            parser,
            parserOptions: {
                ecmaVersion: 2020,
                sourceType: 'module',
                ecmaFeatures: {
                    jsx: true,
                },
            },
        },
        plugins: {
            '@next/next': pluginNext,
        },
        files: ["**/*.js", "**/*.mjs", "**/*.cjs", "**/*.ts", "**/*.tsx"],
        rules: {
            ...pluginNext.configs.recommended.rules,
            ...pluginNext.configs['core-web-vitals'].rules,
        },
    },

    ...tsEslint.configs.recommended,
    {
        rules: {
            /*
            Desabilita a verificação de expressão não usada, que gera o aviso/erro "Expected an assignment or function call and instead saw an expression".
            Tal aviso é causado quando temos uma expressão como condition && functionCall(),
            onde verificamos uma condição (normalmente por meio de uma variável) e chamamos uma função caso a condição seja verdadeira.
            Isto é uma forma sucinta de evitar escrever um if para chamar a função.
            No entanto, como a forma que a linha acima foi escrita representa uma expressão que terá um valor
            e tal valor não está sendo atribuído a nenhuma variável nem retornado por uma função,
            é considerado que a expressão não está sendo usada.
            No entanto, tal técnica é muito usada para exibir componentes condicionalmente no React com JSX
            e é muito prática em código JS convencional. Assim, desabilita tal alerta.
            */
            "@typescript-eslint/no-unused-expressions": "off"
        },
    },
    {
        plugins: {
            'react-hooks': hooksPlugin,
        },
        rules: {
            'react/react-in-jsx-scope': 'off',
            ...hooksPlugin.configs.recommended.rules,
        },
        ignores: ['*.test.tsx'],
    },
    pluginReact.configs.flat['jsx-runtime'],
];

module.exports = {
  parserOptions: {
    ecmaVersion: 'latest',
  },

  env: {
    es6: true,
    node: true,
  },
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-recommended',
  ],
  rules: {
    indent: ['error', 2],
    quotes: [2, 'single', 'avoid-escape'],
    'quote-props': ['error', 'as-needed'],
    'comma-dangle': ['error', 'always-multiline'],
    'no-unused-vars': [
      'error',
      {
        varsIgnorePattern: '^_',
        argsIgnorePattern: '^_',
      },
    ],
    'no-extra-semi': 'error',
    'vue/multi-word-component-names': 'off',
  },
};

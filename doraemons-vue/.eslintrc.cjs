
module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
  ],
  parserOptions: {
    parser: 'babel-eslint',
    ecmaVersion: 2021,
  },
  rules: {
    // 自定义规则

    'vue/multi-word-component-names': 0,
  },
  globals: {
    require: true,
  },
};

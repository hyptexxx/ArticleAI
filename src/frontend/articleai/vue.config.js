module.exports = {
  pages: {
    index: {
      entry: './src/main.ts',
      template: 'public/index.html',
      filename: 'index.html',
      title: 'Index Page',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    },
    test: {
      entry: './src/main.ts',
      template: 'public/test.html',
      filename: 'test.html',
      title: 'test Page',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  }
}

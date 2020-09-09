const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports = {
  css: {
    extract: {
      filename: 'css/Main.css'
    }
  },
  configureWebpack: {
    module: {
      rules: [
        {
          test: /\.twig$/,
          use: {
            loader: 'twig-loader',
            options: {}
          }
        }
      ]
    },
    devServer: {
      headers: { "Access-Control-Allow-Origin": "*" },
      proxy: 'http://localhost:8080'
    },
    node: {
      fs: 'empty'
    },
    optimization: {
      splitChunks: false
    },
    output: {
      filename: 'js/main.js'
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: 'src/html/main.twig',
        filename: 'main.html',
        title: 'stats'
      })
    ]
  }
}

const path = require('path');
const HtmlWebPackPlugin = require("html-webpack-plugin");

const paths = {
    src: path.resolve('./src')
};

module.exports = {
  name: 'fredin.io',
  entry: './src/index.tsx',
  resolve: {
    extensions: [
      '.ts',
      '.tsx',
      '.js',
      '.json',
      '.jsx',
    ]
  },
  devtool: "cheap-module-source-map",
  module: {
    strictExportPresence: true,
    rules: [
      {
        test: /\.(ts|tsx)$/,
        use: 'tslint-loader',
        enforce: 'pre',
        include: paths.src
      },
      {
        test: /\.js$/,
        use: 'source-map-loader',
        enforce: 'pre',
        include: paths.src,
      },
      {
        oneOf: [
          {
            test: [/\.bmp$/, /\.gif$/, /\.jpe?g$/, /\.png$/, /\.svg$/],
            use: {
              loader: 'file-loader',
              options: {
                name: '[path][name]-[hash:8].[ext]'
              }
            }
          },
          {
            test: /\.(ts|tsx)$/,
            use: 'ts-loader',
            include: paths.src,
          },
          {
            test: /\.(css|less)$/,
            use: ['style-loader', 'css-loader', 'less-loader'],
            include: paths.src
          },
          {
            test: /\.css$/,
            use: ['style-loader', 'css-loader'],
          },
          {
            exclude: [/\.js$/, /\.html$/, /\.json$/],
            use: 'file-loader',
          },
        ],
      },
    ],
  },
  plugins: [
    new HtmlWebPackPlugin({
      template: './src/index.html',
      filename: './index.html'
    })
  ]
};

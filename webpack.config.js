const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const PreloadQueriesPlugin = require("domainql-webpack-plugin");
const JsViewPlugin = require("jsview-webpack-plugin");
const TrackUsagePlugin = require("babel-plugin-track-usage/webpack/track-usage-plugin");
const AutomatonPlugin = require("babel-plugin-automaton/webpack/AutomatonPlugin");

const path = require("path");
const fs = require("fs");
const webpack = require("webpack");
const shellJs = require("shelljs");

const PRODUCTION = (process.env.NODE_ENV === "production");

// TODO: change js output directory matching maven target 
const JS_OUTPUT_DIRECTORY = path.join(__dirname, "target/automatontemplate/js/");

if (!fs.existsSync(JS_OUTPUT_DIRECTORY))
{
    shellJs.mkdir("-p", JS_OUTPUT_DIRECTORY);
}

module.exports = {
    mode: process.env.NODE_ENV,
    entry: {
        "myapp": "./src/main/js/apps/myapp/myapp-startup.js",
        "v-login": "./src/main/js/login.js",
    },

    devtool: "source-map",

    output: {
        path: JS_OUTPUT_DIRECTORY,
        filename: "bundle-[name]-[chunkhash].js",
        chunkFilename: "bundle-[name]-[chunkhash].js",

        // TODO: change exposed entry-point
        library: "App",
        libraryTarget: "var",
        libraryExport: "default",
    },

    // aliases to be able to use "yarn link automaton-js"
    resolve: {
        alias: {
            "react": path.resolve("./node_modules/react"),
            "react-dom": path.resolve("./node_modules/react-dom"),
            "create-react-class": path.resolve("./node_modules/create-react-class"),
            "mobx": path.resolve("./node_modules/mobx"),
            "domainql-form": path.resolve("./node_modules/domainql-form"),
            "reactstrap": path.resolve("./node_modules/reactstrap"),
            "mobx-react": path.resolve("./node_modules/mobx-react"),
            "mobx-react-lite": path.resolve("./node_modules/mobx-react-lite"),
            "mobx-react-devtools": path.resolve("./node_modules/mobx-react-devtools"),
            "mobx-utils": path.resolve("./node_modules/mobx-utils"),
            "history": path.resolve("./node_modules/history"),
            "bignumber.js": path.resolve("./node_modules/bignumber.js"),
            "react-calendar": path.resolve("./node_modules/react-calendar")
        }
    },

    plugins: (function () {
        const activePlugins = [
            new MiniCssExtractPlugin({
                filename: "bundle-[name]-[chunkhash].css",
                chunkFilename: "bundle-[id]-[chunkhash].css"
            }),

            new webpack.DefinePlugin({
                "__PROD": PRODUCTION,
                "__DEV": !PRODUCTION,
                "process.env.NODE_ENV": JSON.stringify(PRODUCTION ? "production" : "development")
            }),

            // clean old assets and generate webpack-assets.json
            new JsViewPlugin(),
            new PreloadQueriesPlugin({
                //debug: true
            }),
            new TrackUsagePlugin({
                output: path.join( JS_OUTPUT_DIRECTORY, "/track-usage.json")
            })
        ];

        if (process.env.BABEL_ENV === "analyze")
        {
            activePlugins.push(
                new AutomatonPlugin({
                    output: path.join( "./src/main/webapp/WEB-INF/automaton"),
                    debug: true
                })
            );
        }

        return activePlugins;
    })(),

    module: {
        rules: [
            // babel transpilation ( see .babelrc for babel config)
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader"
                }
            },

            // this is just concatenating the .css modules in components to one bundle.
            // No postprocessing of that.
            {
                test: /\.css$/,
//                exclude: /node_modules/,
                use: [MiniCssExtractPlugin.loader, "css-loader"]
            },

            {
                test: /\.svg$/,
                use: ['@svgr/webpack'],
            }
        ]
    },

    optimization: {
        splitChunks: {
            cacheGroups: {
                commons: {
                    test: /[\\/]node_modules[\\/]/,
                    name: "vendors",
                    chunks: "all"
                }
            }
        }
    }
};

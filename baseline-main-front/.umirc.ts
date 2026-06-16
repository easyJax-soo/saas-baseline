import { defineConfig } from "umi"
import Routers from "./src/routers/routers"
import path from "path"

const BASE_PATH = process.env.BASE_PATH
const CAPTCH = process.env.CAPTCH

export default defineConfig({
    plugins: ["@umijs/plugins/dist/dva"],
    define: { "process.env.CAPTCH": CAPTCH },
    dva: {},
    routes: [...Routers],
    npmClient: "yarn",
    outputPath: "dist",
    base: BASE_PATH || "/",
    publicPath: BASE_PATH || "/",
    ignoreMomentLocale: true,
    codeSplitting: {
        jsStrategy: "granularChunks",
    },
    styles: [`body { margin:0px;overflow:hidden;background-color: rgba(247, 247, 250, 1) }`],
    alias: {
        "@": path.resolve(__dirname, "./src"),
        "&": path.resolve(__dirname, `./src/pages`),
    },
    proxy: {
        "/api": {
            target: "http://localhost:38080",
            changeOrigin: true,
            pathRewrite: {
                "^/api": "",
            },
        },
    },
})

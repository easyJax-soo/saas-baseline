import "umi/typings"

declare module "*.less"
declare module "*.png"
declare module "*.svg"

declare namespace NodeJS {
    interface ProcessEnv {
        CAPTCH?: string
        BASE_PATH?: string
    }
}

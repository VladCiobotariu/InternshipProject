import { createBreakpoint } from "react-use"
import screens from "../tailwind.screens"

export const breakpoints = {
    sm: Number.parseInt(screens.sm.max),
    md: Number.parseInt(screens.md.min),
    lg: Number.parseInt(screens.lg.min),
    xl: Number.parseInt(screens.xl.min),
    xxl: Number.parseInt(screens.xl.max)
}

const useBreakpoint = createBreakpoint(breakpoints)
export default useBreakpoint
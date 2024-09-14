import { useEffect, useState } from "react"
import SignupHeader from "./header/SignupHeader"
import SignupBody from "./body/SignupBody"
export default function SignupPage(){

    return(
        <div className="page">
            <SignupHeader/>
            <SignupBody/>
        </div>
    )
}
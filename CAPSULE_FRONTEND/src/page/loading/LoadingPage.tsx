import "../../styles/loading/loading.css"
import Icon from "../../images/capsule.svg"
import { useEffect } from "react"

export default function LoadingPage(){
    useEffect(()=>{
        localStorage.clear();

    },[])
    return(
        <div className="loading_page">
            <img src={Icon} alt="icon" className="loading-icon"/>
        </div>
    )
}
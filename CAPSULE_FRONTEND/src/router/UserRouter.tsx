import { useEffect, useState} from 'react';
import { Route, Routes } from "react-router-dom";
import LoadingPage from "../page/loading/LoadingPage";
import LoginPage from '../page/login/LoginPage';
import RedirectToken from '../page/auth/RedirectToken';
import SignupPage from '../page/signup/SignupPage';
import MainPage from '../page/main/MainPage';
import ReadCapsule from '../page/readCapsule/ReadCapsule';
import CreateCapsule from '../page/createCapsule/CreateCapsule';
import SendAnimation from '../page/motion/SendAnimation';
import ReciveAnimation from '../page/motion/ReciveAnimation';
import WritePage from '../page/write/WritePage';
import InfoPage from '../page/info/InfoPage';
import RequestPage from '../page/request/RequestPage';
import SearchPage from '../page/search/SearchPage';
import FriendInfoPage from '../page/Friend/FriendInfoPage';


export default function UserRouter(){

    const [isLoading, setIsLoading] = useState<boolean>(false);
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    useEffect(()=>{
        setTimeout(()=>{
            setIsLoading(true);
          },4000)
    },[])

    return(
        <Routes>
            <Route path="/" element={isLoading ? <LoginPage/> : <LoadingPage/>}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path="/oauth/:token" element={<RedirectToken/>}/>
            <Route path='/signup' element={<SignupPage/>}/>
            <Route path='/main' element={accessToken == null ? <LoginPage/> : <MainPage/>}/>
            <Route path="/capsule/:capsuleId" element = {accessToken == null ? <LoginPage/> : <ReadCapsule/>}/>
            <Route path="/create/capsule" element = {accessToken == null ? <LoginPage/> : <CreateCapsule/>}/>
            <Route path='receive/:capsuleId' element={accessToken == null ? <LoginPage/> :<ReciveAnimation/>}/>
            <Route path='send/:capsuleId' element={accessToken == null ? <LoginPage/> :<SendAnimation/>}/>
            <Route path='/write/:capsuleId' element={accessToken == null ? <LoginPage/> :<WritePage/>}/>
            <Route path='/info' element={accessToken == null ? <LoginPage/> :<InfoPage/>}/>
            <Route path='/friend/list' element={accessToken ==null ? <LoginPage/>:<RequestPage/>}/>
            <Route path='/search' element={accessToken ==null ? <LoginPage/>:<SearchPage/>}/>
            <Route path='/friend/profile/:email' element={<FriendInfoPage />} />
        </Routes>
    )
}
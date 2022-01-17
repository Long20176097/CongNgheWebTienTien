import './ViewDetailedUser.css'

import { Link, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";

import axios from "axios";
import dateFormat from 'dateformat';

const ViewDetailedUser = props => {



  const token = localStorage.getItem('jwttoken')
    
  const headers = { 
    'Authorization': token
    
};
  const rootAPI = process.env.REACT_APP_SERVER_URL;
  let {id} = props;
  const [user, setUser] = useState({
    id: "",
 
    username: "",
    first_name: "",
    last_name: "",
    authority: "",
    dob: "",
    joined_date: "",
    phone_number:"",
    link_avatar:"",
    status:""
  });
  
  useEffect(() => {
    loadUser();
  }, []);
  const loadUser = async () => {
    const res = await axios.get(rootAPI+`/users/${id}` ,{headers});
    setUser(res.data);
  };

  const checkStatus = (status) => {
    if(status == 0){
      return <span>Disable</span>
    }
    if(status == 1){
      return <span>Enabled</span>
    }
  }
  return (
   <div >
    <div><h3 className="title-detail-user">
         Detailed information of user
         </h3></div>  
     <div>
     <table> 
       <tbody>
       <tr>
         <td className="fields-name">ID </td>
         <td>: {user.id}</td>
       </tr>
       <tr>
         <td className="fields-name">Username</td>
         <td>: {user.username}</td>
       </tr>
       <tr>
         <td className="fields-name">First name </td>
         <td>: {user.firstName}</td>
       </tr>
       <tr>
         <td className="fields-name">Last name </td>
         <td>: {user.lastName}</td>
       </tr>
       <tr>
         <td className="fields-name">Type </td>
         <td>: {user.authority}</td>
       </tr>
       <tr>
         <td className="fields-name">Date of Birth </td>
         <td>: {dateFormat(user.dob, "dd/mm/yyyy")}</td>
       </tr>
       <tr>
         <td className="fields-name">Joined Date </td>
         <td>: {dateFormat(user.joinedDate, "dd/mm/yyyy")}</td>
       </tr>
       <tr>
         <td className="fields-name">Phone Number </td>
         <td>: {user.phoneNumber} </td>
       </tr>
       <tr>
         <td className="fields-name">Link Avatar </td>
         <td>: {user.linkAvatar} </td>
       </tr>
       <tr>
         <td className="fields-name">Status </td>
         <td>: {user.status} </td>
       </tr>
       </tbody>
     </table>
     </div>
    </div>
    
  );
};

export default ViewDetailedUser;
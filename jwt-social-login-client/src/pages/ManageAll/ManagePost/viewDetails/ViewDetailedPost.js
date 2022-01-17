import './ViewDetailedPost.css'

import { Link, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";

import axios from "axios";
import dateFormat from 'dateformat';

const ViewDetailedPost = props => {



  const token = localStorage.getItem('jwttoken')
    
  const headers = { 
    'Authorization': token
    
};
  const rootAPI = process.env.REACT_APP_SERVER_URL;
  let {id} = props;
  const [post, setPost] = useState({
    id: "",
  

 

    author: "",

    updated: "",

    content:"",
    media:""
  });
  
  useEffect(() => {
    loadPost();
  }, []);
  const loadPost = async () => {
    const res = await axios.get(rootAPI+`/posts/${id}` ,{headers});
    setPost(res.data);
  };


  return (
   <div >
    <div><h3 className="title-detail-post">
         Detailed information of post
         </h3></div>  
     <div>
     <table> 
       <tbody>
       <tr>
         <td className="fields-name">Media </td>
         <td>: {post.media}</td>
       </tr>
       <tr>
         <td className="fields-name">Content</td>
         <td>: {post.content}</td>
       </tr>
       <tr>
         <td className="fields-name">Author ID </td>
         <td>: {post.author}</td>
       </tr>
       <tr>
         <td className="fields-name">Updated </td>
         <td>: {dateFormat(post.updated, "dd/mm/yyyy")}</td>
       </tr>
      
       </tbody>
     </table>
     </div>
    </div>
    
  );
};

export default ViewDetailedPost;
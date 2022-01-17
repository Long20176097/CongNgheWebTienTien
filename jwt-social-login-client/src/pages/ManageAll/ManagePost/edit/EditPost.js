import 'bootstrap/dist/css/bootstrap.min.css';

import * as Yup from "yup";

import {Button, Form, FormCheck, FormControl, Row} from "react-bootstrap";
import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from 'react-router-dom';
import '../../../../style/style.css'
import {Formik} from 'formik';
import axios from "axios";
import differenceInDays from 'date-fns/differenceInDays/index.js';
import {differenceInYears} from "date-fns";

const EditPost = ({setResponsePost, setChildPage}) => {

    const token = localStorage.getItem('jwttoken')

    const headers = {
        'Authorization': token

    };
    const rootAPI = process.env.REACT_APP_SERVER_URL;
    let {id} = useParams();
    const history = useHistory();
    const [post, setPost] = useState({
        id: null,
        content: null,
        media: null,
        updated: null,
        author:null
       
    });
    useEffect(() => {
        axios
            .get(rootAPI + `/posts/${id}`, {headers})
            .then(function (response) {
                setPost(response.data);
                setGender(response.data.gender);
            });
    }, [id])

    const [gender, setGender] = useState("");
    const initialValues = {

        content: post.content,
        media: post.media,
        updated: post.updated,
        author:post.author
    

    }
    const onSubmit = (values, {setSubmitting}) => {
        let editPost = {
      
            content: values.content,
            media: values.media,
            updated: values.updated,
            author_id: post.author
        }
        axios
            .put(rootAPI + `/posts/${id}`, editPost, {headers})
            .then((response) => {
                setSubmitting(false);
                setResponsePost({
                    id: response.data.id,
                 
                    content: response.data.content,
                    media: response.data.media,
                    updated: response.data.updated,
                    author: response.data.author
                    
                });
                setChildPage(null);
                history.push("/post");
            });
    };
    const ValidateSchema = Yup.object().shape({
        content: Yup.string()
        .max(1000)
        .required()
        .typeError("Content is required"),
        media: Yup.string()
        .max(1000)
        .required()
        .typeError("Media is required"),
     
        updated: Yup.date()
            .required()
            .typeError("Updated is required")
          
    });

    function onKeyDown(keyEvent) {
        if ((keyEvent.charCode || keyEvent.keyCode) === 13) {
            keyEvent.preventDefault();
        }
    }

    return (
        <div className={"container ps-5 d-block"}>
            <Row>
                <h1 className={"text-primary mb-5"}>Edit Post</h1>
            </Row>
            <Row className={"mt-5 justify-content-start"}>
                <Formik
                    initialValues={initialValues}
                    onSubmit={onSubmit}
                    enableReinitialize={"true"}
                    validationSchema={ValidateSchema}
                >
                    {({
                          values,
                          errors,
                          touched,
                          handleChange,
                          handleBlur,
                          handleSubmit,
                          isSubmitting,
                          /* and other goodies */
                      }) => (
                        <Form onSubmit={handleSubmit}
                              onKeyDown={onKeyDown}
                              className={"col-7"}
                        >
                              <Row className={"mb-3"}>
                                <p className={"w-25"}>Media</p>
                                <FormControl
                                   
                                    aria-label="Postname"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name="media"
                                    style={{backgroundColor: "#eff1f5"}}
                                    value={values.media}
                                    onChange={handleChange}
                                    onError={errors}
                                    onBlur={handleBlur}
                                    isValid={touched.media && !errors.media}
                                    isInvalid={touched.media && errors.media}
                                />
                                {errors.media && touched.media ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.media}
                                    </div>
                                ) : null}
                            </Row>
                                <Row className={"mb-3"}>
                                <p className={"w-25"}>Content</p>
                                <FormControl
                                   
                                    aria-label="Postname"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name="content"
                                    style={{backgroundColor: "#eff1f5"}}
                                    value={values.content}
                                    onChange={handleChange}
                                    onError={errors}
                                    onBlur={handleBlur}
                                    isValid={touched.content && !errors.content}
                                    isInvalid={touched.content && errors.content}
                                />
                                {errors.content && touched.content ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.content}
                                    </div>
                                ) : null}
                            </Row>

                         
                       
                            <Row className="mb-3">
                                <p className={"w-25"} id="basic-addon1">
                                    Updated
                                </p>
                                <FormControl
                                    type={"date"}
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"updated"}
                                    value={values.updated}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    isValid={touched.updated && !errors.updated}
                                    isInvalid={touched.updated && errors.updated}
                                />
                                {errors.updated && touched.updated ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.updated}
                                    </div>
                                ) : null}
                            </Row>
                         
                            <Row className={"justify-content-end"}>
                                <Button
                                    variant={"primary"}
                                    type="submit"
                                    style={{width: "100px"}}
                                >
                                    SAVE
                                </Button>
                                <Button
                                    variant={"secondary"}
                                    type={"submit"}
                                    className={"ms-5"}
                                    style={{width: "100px"}}
                                    onClick={() => {
                                        setChildPage(null);
                                        history.push("/post")
                                    }}
                                >
                                    CANCEL
                                </Button>

                            </Row>
                        </Form>
                    )}
                </Formik>
            </Row>
        </div>
    );
};

export default EditPost;
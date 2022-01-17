import 'bootstrap/dist/css/bootstrap.min.css';
import '../../../../style/style.css'

import * as Yup from "yup";

import {Button, Form, FormCheck, FormControl, Row} from "react-bootstrap";

import {Formik} from 'formik';
import React from 'react';
import axios from "axios";
import differenceInDays from 'date-fns/differenceInDays/index.js';
import {differenceInYears} from "date-fns";
import {useHistory} from 'react-router-dom';

const CreateUser = ({setResponseUser, setChildPage}) => {

    const token = localStorage.getItem('jwttoken')

    const headers = {
        'Authorization': token

    };
    const rootAPI = process.env.REACT_APP_SERVER_URL;
    const history = useHistory();
    const initialValues = {
        id: null,
        
        firstName: null,
        lastName: null,
        username: null,
        dob: null,
        gender: null,
        joinedDate: null,
        phoneNumber: null,
        linkAvatar: null,
        authority: null,
        password: null

    }
    const onSubmit = (values, {setSubmitting}) => {
        let create = {
            username: values.username,
            first_name: values.firstName,
            last_name: values.lastName,
            dob: values.dob,
            gender: values.gender,
            joined_date: values.joinedDate,
            link_avatar: values.linkAvatar,
            phone_number: values.phoneNumber,
            authority: values.authority,
            password:values.password
        }

        axios
            .post(rootAPI + `/users`, create, {headers})
            .then((response) => {
                setSubmitting(false);
                setResponseUser({
                    id: response.data.id,
                    phoneNumber:response.data.phoneNumber,
                    username:response.data.username,
                    linkAvatar: response.data.linkAvatar,
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    username: response.data.username,
                    dob: response.data.dob,
                    gender: response.data.gender,
                    joinedDate: response.data.joinedDate,
                    authority: response.data.authority,
                    password : response.data.password
                });
                setChildPage(null);
                history.push("/user");
            });
    };
    const ValidateSchema = Yup.object().shape({
        username: Yup.string()
        .max(255)
        .required('Required')
        .typeError('Username is required'),
        phoneNumber: Yup.string()
        .max(255)
        .required('Required')
        .typeError('Phone number is required'),
        linkAvatar: Yup.string()
        .max(255)
        .required('Required')
        .typeError('Link avatar is required'),
        password: Yup.string()
        .required('No password provided.') 
        .min(8, 'Password is too short - should be 8 chars minimum.')
        .matches(/[a-zA-Z]/, 'Password can only contain Latin letters.'),
        firstName: Yup.string()
            .max(255)
            .required('Required')
            .typeError('First name is required')
            .test("firstName", "First Name is only 1 word", function (value) {
                return !/\s/g.test(value);
            }),
        lastName: Yup.string()
            .max(255)
            .required('Required')
            .typeError('Last name is required'),
        dob: Yup.date()
            .required()
            .typeError('DOB is required')
            .test("dob", "User is under 18. Please select a different date", function (value) {
                return differenceInYears(new Date(), new Date(value)) >= 18;
            }),
        authority: Yup.string()
            .required('Required')
            .typeError('Please select type'),
        joinedDate: Yup.date()
            .required()
            .typeError('Joined Date is required')
            .min(Yup.ref('dob'), "Joined date is not later than Date of Birth. Please select a different date")
            .test("dob", "Joined date is not later than Date of Birth. Please select a different date", function (value) {
                return differenceInDays(new Date(value), new Date(this.parent.dob)) != 0;
            })
            .test("dob", "Joined date is Saturday or Sunday. Please select a different date", function (value) {
                const currentDate = new Date(value);
                return (
                    currentDate.getDay() !== 6 &&
                    currentDate.getDay() !== 0
                );
            })
        ,
    });
    const formValid = values => {
        return (values.firstName === null
            || values.lastName === null
            || values.dob === null
            || values.authority === null
            || values.installedDate === null
        )
    }
    return (
        <div className={"container ps-5 d-block"}>
            <Row>
                <h1 className={"text-primary mb-5"}>Create New User</h1>
            </Row>
            <Row className={"mt-5 justify-content-start"}>
                <Formik
                    initialValues={initialValues}
                    onSubmit={onSubmit}
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
                        <Form onSubmit={handleSubmit} className={"col-7"}>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>Phone number</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"phoneNumber"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.phoneNumber && !errors.phoneNumber}
                                    isInvalid={touched.phoneNumber && errors.phoneNumber}
                                />
                                {errors.phoneNumber && touched.phoneNumber ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.phoneNumber}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>Username</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"username"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.username && !errors.username}
                                    isInvalid={touched.username && errors.username}
                                />
                                {errors.username && touched.username ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.username}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>Password</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"password"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.password && !errors.password}
                                    isInvalid={touched.password && errors.password}
                                />
                                {errors.password && touched.password ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.password}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>Link avatar</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"linkAvatar"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.linkAvatar && !errors.linkAvatar}
                                    isInvalid={touched.linkAvatar && errors.linkAvatar}
                                />
                                {errors.linkAvatar && touched.linkAvatar ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.linkAvatar}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>First Name</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"firstName"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.firstName && !errors.firstName}
                                    isInvalid={touched.firstName && errors.firstName}
                                />
                                {errors.firstName && touched.firstName ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.firstName}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"mb-3"}>
                                <p className={"w-25"}>Last Name</p>
                                <FormControl
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    className={"w-75"}
                                    name={"lastName"}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    isValid={touched.lastName && !errors.lastName}
                                    isInvalid={touched.lastName && errors.lastName}
                                />
                                {errors.lastName && touched.lastName ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.lastName}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className="mb-3">
                                <p className={"w-25"} id="basic-addon1">
                                    Date of Birth
                                </p>
                                <FormControl
                                    type={"date"}
                                    aria-describedby="basic-addon1"
                                    className={"w-75 custom-checkbox"}
                                    name={"dob"}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    isValid={touched.dob && !errors.dob}
                                    isInvalid={touched.dob && errors.dob}
                                />
                                {errors.dob && touched.dob ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.dob}
                                    </div>
                                ) : null}
                            </Row>
                            <Row>
                                <p id="basic-addon1" className={"w-25"}>
                                    Gender
                                </p>
                                <div className={"container-lg w-75 d-flex"}>
                                    <FormCheck
                                        inline
                                        type={"radio"}
                                        label={"Female"}
                                        className={"w-50"}
                                        name={"gender"}
                                        onChange={() => (values.gender = "Female")}
                                    />
                                    <FormCheck
                                        inline
                                        type={"radio"}
                                        label={"Male"}
                                        className={"w-50"}
                                        name={"gender"}
                                        onChange={() => (values.gender = "Male")}
                                    />
                                </div>
                            </Row>
                            <Row className="mb-3">
                                <p className={"w-25"} id="basic-addon1">
                                    Joined Date
                                </p>
                                <FormControl
                                    type={"date"}
                                    className={"w-75"}
                                    name={"joinedDate"}
                                    placeholder={"1999-01-01"}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    isValid={touched.joinedDate && !errors.joinedDate}
                                    isInvalid={touched.joinedDate && errors.joinedDate}
                                />
                                {errors.joinedDate && touched.joinedDate ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.joinedDate}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className="mb-3">
                                <p className={"col-3"}>Type</p>
                                <Form.Select
                                    size="sm"
                                    className={"w-75"}
                                    name={"authority"}
                                    value={values.authority}
                                    onChange={handleChange}
                                    isValid={touched.authority && !errors.authority}
                                    isInvalid={touched.authority && errors.authority}
                                    onBlur={handleBlur}
                                >
                                    <option selected></option>
                                    <option value={"admin"}>Admin</option>
                                    <option value={"user"}>User</option>
                                </Form.Select>
                                {errors.authority && touched.authority ? (
                                    <div
                                        className={"text-danger"}
                                        style={{paddingLeft: "25%"}}
                                    >
                                        {errors.authority}
                                    </div>
                                ) : null}
                            </Row>
                            <Row className={"justify-content-end"}>
                                <Button
                                    variant={"primary"}
                                    type={"submit"}
                                    style={{width: "100px"}}
                                    disabled={formValid(values)}
                                >
                                    SAVE
                                </Button>
                                <Button
                                    variant={"secondary"}
                                    onClick={() => {
                                        setChildPage(null);
                                        history.push("/user");
                                    }}
                                    type={"submit"}
                                    className={"ms-5"}
                                    style={{width: "100px"}}
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

export default CreateUser;
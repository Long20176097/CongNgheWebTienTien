import 'bootstrap/dist/css/bootstrap.min.css'
import './Manage.css'
import 'reactjs-popup/dist/index.css';
import {Button, Container, Form, FormControl, InputGroup, Row, Table} from 'react-bootstrap';
import {useEffect, useRef, useMemo, useState} from 'react';
import Delete from "../delete/Delete";
import DeleteFail from "../delete/DeleteFail";
import Pagination from '../../../../components/Pagination/Pagination'
import Popup from "reactjs-popup";
import React from 'react';
import ViewDetailedPost from "../viewDetails/ViewDetailedPost"
import axios from "axios";
import {useHistory} from 'react-router-dom'
import dateFormat from 'dateformat';

import '../../../../style/style.css'


const ManageFriend = ({responsePost, setChildPage, setCurrentPages, setResponsePost}) => {

    const token = localStorage.getItem('jwttoken')
    const headers = {
        'Authorization': token
    };
    
    const rootAPI = process.env.REACT_APP_SERVER_URL;
    const [currentPage, setCurrentPage] = useState(1);
    const [usersPerPage] = useState(10);
    const [sortConfig, setSortConfig] = useState(null);
    const indexOfLastUser = currentPage * postsPerPage;
    const indexOfFirstUser = indexOfLastUser - usersPerPage;
    const [refreshList, setRefreshList] = useState(false);
    const [disable, setDisable] = useState("false");
    const paginate = pageNumber => setCurrentPage(pageNumber);
    const history = useHistory();

    const [list, setList] = useState([{
        id:null,
        userA: null,
        userB: null,
        state: null,
        created: null
    
  
     
    }]);
    console.log(list)
    useEffect(() => {
        axios.get(rootAPI + '/posts', {headers})
            .then(function (response) {
                setDisable(false);
              
                setCurrentPages("Manage Post")
                let result = response.data.map(post => post.id);
                if (responsePost && result.includes(responsePost.id)) {
                    const index = result.indexOf(responsePost.id);
                    const newPost = response.data.splice(index, 1)[0];
                    response.data.unshift(newPost);
                    setList(response.data);
                    setResponsePost(null);
                } else {
                    setList(response.data);
                }  
                setRefreshList(false);
            })
    }, [refreshList])

    const [type, setType] = useState();
    const [searchTerm, setSearchTerm] = useState();
    const request = {
        headers: headers,
        params: {
            type,
            searchTerm
        }
    }

    const handleFilterType = evt => {
        setType(evt.target.value)
    }

    const handleSearch = evt => {
        setSearchTerm(evt.target.value)
    }

    const isFirstRun = useRef(true);
    useEffect(() => {
        if (isFirstRun.current) {
            isFirstRun.current = false;
            return;
        }
        // if (request.params.type === 'Type') {
        //     request.params.type = null;

        // }
        if (request.params.searchTerm === '') {
            request.params.searchTerm = null;

        }
        axios.get(rootAPI + '/posts', request)
            .then(function (response) {
                setCurrentPage(1);
                setList(response.data);
            })
    }, [type, searchTerm])

    const sortingData = useMemo(() => {
        let listData = list;
        if (sortConfig !== null) {
            listData.sort((a, b) => {
                if (a[sortConfig.key] < (b[sortConfig.key])) {
                    return sortConfig.direction === "asc" ? -1 : 1;
                }
                if (a[sortConfig.key] > (b[sortConfig.key])) {
                    return sortConfig.direction === "asc" ? 1 : -1;
                }
                return 0;
            })
        }
    }, [list, sortConfig]);

    const requestSort = key => {
        let direction = "asc";
        if (sortConfig && sortConfig.key === key && sortConfig.direction === "asc") {
            direction = "desc";
        }
        setSortConfig({key, direction});
    }

    const getClassNamesFor = (name) => {
        if (!sortConfig) {
            return;
        }
        return sortConfig.key === name ? sortConfig.direction : undefined;
    };

    // function capitalizeFirstLetter(string) {
    //     return string?.charAt(0) + string?.slice(1).toLowerCase();
    // }

    return (
        <Container fluid className={"d-block ps-5"}>
            <h3 className={"text-primary mb-3"}>Post List</h3>
            <div className={"justify-content-between d-flex"}>
                {/* <div className={"col-3 d-flex"}>
                    <InputGroup className={"w-50"}>
                        <Form.Control
                            as="select"
                            custom
                            placeholder={"Type"}
                            name={"type"}
                            onChange={handleFilterType}
                            className={"border-end-0 border-secondary"}
                        >
                            <option value={"Type"}>Type</option>
                            <option value="admin">Admin</option>
                            <option value="post">Post</option>
                        </Form.Control>
                        <Button variant={"outline-secondary"}
                                className={"border-start-0"}
                        >
                            <i className="bi bi-funnel-fill"/>
                        </Button>
                    </InputGroup>
                </div> */}
                <div className={"col-6 d-flex justify-content-end"}>
                    <InputGroup className={"w-50"}>
                        <FormControl
                            type={"text"}
                            name={"searchTerm"}
                            onChange={handleSearch}
                            maxLength={255}
                            className={"border-end-0 border-secondary"}
                        />
                        <Button variant={"outline-secondary"}
                                className={"border-start-0"}
                        >
                            <i className="bi bi-search"/>
                        </Button>
                    </InputGroup>
                    <Button
                        variant={"primary"}
                        className={"w-auto ms-5"}
                        onClick={() => {
                            setChildPage("Create New Post");
                            history.push("/createpost");
                        }}
                    >
                        Create new post
                    </Button>
                </div>
            </div>
            <Row className={"mt-5"}>
                <Table>
                    <thead>
                    <tr>
                    <th
                            className={"border-bottom"}
                            className={getClassNamesFor("id")}
                            onClick={() => requestSort("id")}
                        >
                          ID
                        </th>
                        <th
                            className={"border-bottom"}
                            className={getClassNamesFor("media")}
                            onClick={() => requestSort("media")}
                        >
                            Media
                        </th>
                        <th
                            className={"border-bottom"}
                            className={getClassNamesFor("content")}
                            onClick={() => requestSort("content")}
                        >
                            Content
                        </th>
                
                        <th
                            className={"border-bottom"}
                            className={getClassNamesFor("updated")}
                            onClick={() => requestSort("updated")}
                        >
                            Updated
                        </th>
                        <th
                            className={"border-bottom"}
                            className={getClassNamesFor("author")}
                            onClick={() => requestSort("author")}
                        >
                            Author ID
                        </th>
                       
                    </tr>
                    </thead>
                    <tbody>
                    {list.slice(indexOfFirstPost, indexOfLastPost).map((post) => (
                        <Popup
                            key={post.id}
                            contentStyle={{
                                width: "25%",
                                border: "1px solid black",
                                borderRadius: 10,
                                overflow: "hidden",
                                padding: "20px",
                            }}
                            trigger={
                                <tr key={post.id}>
                                    <td>{post.id}</td>
                                    <td>{post.media}</td>
                                   
                                    <td>{post.content}</td>
                                    <td>{dateFormat(post.updated , "dd/mm/yyyy")}</td>
                                    <td>{post.author}</td>
                                    <td>
                                        <i className="bi bi-pen btn m-0 text-muted p-0 zoomin"
                                           onClick={() => {
                                               setChildPage("Edit Post");
                                               history.push(`/editpost/${post.id}`)
                                           }}
                                        />
                                    </td>
                                    <Popup contentStyle={{
                                    width: "25%", border: "1px solid black", borderRadius: 10,
                                    overflow: 'hidden'
                                }}
                                       trigger={<td><i className="bi bi-x-circle text-danger btn p-0 zoomin"/></td>}
                                       modal
                                       closeOnDocumentClick={false}
                                >
                                   
                                   {(close) => { return <Delete id={post.id}
                                                         close={close}
                                                         setRefreshList={setRefreshList}
                                                         setDisable={setDisable}
                                                         />;
                                            
                                                        }}
                                      
                                </Popup>
                                </tr>
                            }
                            modal
                            disabled={disable}
                        >
                            {(close) => (
                                <div>
                                    <ViewDetailedPost id={post.id}/>
                                    <Button
                                        onClick={close}
                                        variant="success"
                                        className="btn-view-detail"
                                    >
                                        &times;
                                    </Button>
                                </div>
                            )}
                        </Popup>
                    ))}
                    </tbody>
                </Table>
            </Row>
            <Pagination
                className="pagnition"
                postsPerPage={postsPerPage}
                totalPosts={list.length}
                paginate={paginate}
            />
        </Container>
    );
};

export default ManagePost;
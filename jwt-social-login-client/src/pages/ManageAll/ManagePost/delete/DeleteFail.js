import React from 'react';
import { Button, Row } from 'react-bootstrap';

const DeleteFail = props => {
    let {id, close} =props
    return (
      <div>
        <Row className={"justify-content-between align-items-center"}
            style={{padding: '10px 20px'}}
        >
          <h3 className={"text-danger w-auto m-0 p-0"}>Can not disable post</h3>
          <Button variant={"outline-danger w-auto"} style={{padding: '3px 6px'}} onClick={() => close()}>
            <i class="bi bi-x-lg"/>
          </Button>
        </Row>
        <hr style={{margin: '0'}}/>
        <p style={{padding: '10px'}}>
          Cannot delete this post <a href={`/editpost/${id}`}> Edit Post page</a>
        </p>
      </div>
    );
};

export default DeleteFail;
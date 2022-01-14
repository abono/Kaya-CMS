import React, { useState, useReducer, useEffect, useContext } from 'react';
import { Container, Table } from 'reactstrap';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetUnpublishedEntities } from "../services/PublishService";

function Publish() {
  const [ , userContextDispatch ] = useContext(UserContext);

  const [ publishEntities, setPublishEntities ] = useState( { webPageTemplates: [], webPages: [], media: [] } );
  const [ searchState, searchDispatch ] = useReducer(APICallState, APICallInit);

  useEffect(() => {
    userContextDispatch( { type: "ALERT_MESSAGE", payload: "Loading admin users" });
    GetUnpublishedEntities(searchDispatch);
  }, []);

  useEffect(() => {
    if (searchState.isError) {
        userContextDispatch( { type: "ALERT_ERROR", payload: searchState.errorMessage });
    } else if (searchState.data) {
        userContextDispatch( { type: "ALERT_CLOSE" });
    }
    setPublishEntities(searchState.data
        ? searchState.data
        : { webPageTemplates: [], webPages: [], media: [] });
  }, [searchState]);

  const webPageTemplateList = publishEntities.webPageTemplates.map(webPageTemplate => <tr key={webPageTemplate.webPageTemplateId}>
      <td>{webPageTemplate.name} ({webPageTemplate.nameEdits})</td>
      <td></td>
    </tr>
  );

  const webPageList = publishEntities.webPages.map(webPage => <tr key={webPage.webPageId}>
      <td>{webPage.path} ({webPage.pathEdits})</td>
      <td>{webPage.title} ({webPage.titleEdits})</td>
      <td>{webPage.description} ({webPage.descriptionEdits})</td>
      <td></td>
    </tr>
  );

  const mediaList = publishEntities.media.map(media => <tr key={media.mediaId}>
      <td>{media.path} ({media.pathEdits})</td>
      <td></td>
    </tr>
  );

  return <div>
    <Container fluid>
      <h3>Web Page Templates</h3>
      <Table className="mt-4">
          <thead>
          <tr>
              <th>Name</th>
              <th>Action</th>
          </tr>
          </thead>
          <tbody>
              {webPageTemplateList}
          </tbody>
      </Table>

      <h3>Web Pages</h3>
      <Table className="mt-4">
          <thead>
          <tr>
              <th>Path</th>
              <th>Title</th>
              <th>Description</th>
              <th>Action</th>
          </tr>
          </thead>
          <tbody>
              {webPageList}
          </tbody>
      </Table>

      <h3>Media</h3>
      <Table className="mt-4">
          <thead>
          <tr>
              <th>Path</th>
              <th>Action</th>
          </tr>
          </thead>
          <tbody>
              {mediaList}
          </tbody>
      </Table>
    </Container>
  </div>
}

export default Publish;

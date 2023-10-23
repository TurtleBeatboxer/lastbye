import * as model from './form-value-model.cy';
import { lastMessageText } from './generate-values.cy';
export let checkLastMesssage = (three: model.Three) => {
  checkIfFileAdded(three.ifTestament, 'testament');
  checkIfFileAdded(three.ifVideoSpeech, 'videoSpeech');
  checkIfFileAdded(three.ifFarewellLetter, 'farewellLetter');
  checkIfFileAdded(three.ifOther4, 'other4');
  checkFarewellReader(three.ifFarewellLetter, three.farewellReader);
  checkOther4(three.ifOther4, three.other);
};

let checkIfFileAdded = (ifFile: number, fileType: string) => {
  if (ifFile === 0) {
    cy.get(`.${fileType}`).should('contain', lastMessageText[`${fileType}`].yes);
  } else {
    cy.get(`.${fileType}`).should('contain', lastMessageText[`${fileType}`].no);
  }
};

let checkFarewellReader = (ifFarewellLetter: number, farewellReader: string) => {
  if (ifFarewellLetter === 0) {
    cy.get('#farewellReader').should('contain', 'You want your farewell letter to be read by:').should('contain', farewellReader);
  }
};

let checkOther4 = (ifOther: number, other: string) => {
  if (ifOther === 0) {
    cy.get('.other4').should('contain', other);
  }
};

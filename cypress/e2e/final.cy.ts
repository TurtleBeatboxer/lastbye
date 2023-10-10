import * as methods from './methods.cy';

let numberOfTest = 10;

let generateForms = (number: number) => {
  let result: methods.form[] = [];
  for (let i = 0; i < number; i++) {
    result.push(methods.generateFormValues());
  }
  return result;
};

let forms: methods.form[] = generateForms(numberOfTest);

describe('Final tests', () => {
  beforeEach(() => {
    cy.viewport(1920, 1080);
  });

  it('should througly check form ten times', () => {
    for (let i = 0; i < numberOfTest; i++) {
      methods.login(`user${i + 1}`, 'user');
      methods.zeroForm(forms[i].zero);
      methods.skipPicture();
      methods.oneForm(forms[i].one);
      methods.secondForm(forms[i].two);
      methods.thirdForm(forms[i].three);
      methods.fourthForm(forms[i].relative);
      cy.get('.logout').click({ scrollBehavior: false });
    }
  });
});

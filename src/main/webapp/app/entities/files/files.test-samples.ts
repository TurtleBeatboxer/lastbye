import { IFiles, NewFiles } from './files.model';

export const sampleWithRequiredData: IFiles = {
  id: 14378,
};

export const sampleWithPartialData: IFiles = {
  id: 22437,
  type: 'Borders',
};

export const sampleWithFullData: IFiles = {
  id: 30006,
  name: 'Card',
  type: 'Egyptian neural',
  format: 'Computers Operative withdrawal',
  filePath: 'Chicken',
};

export const sampleWithNewData: NewFiles = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

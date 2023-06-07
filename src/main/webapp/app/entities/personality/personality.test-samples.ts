import { IPersonality, NewPersonality } from './personality.model';

export const sampleWithRequiredData: IPersonality = {
  id: 40299,
};

export const sampleWithPartialData: IPersonality = {
  id: 32168,
  relativeEmail: 'Response',
};

export const sampleWithFullData: IPersonality = {
  id: 50193,
  relativeEmail: 'Handcrafted invoice integrate',
};

export const sampleWithNewData: NewPersonality = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonality } from '../personality.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../personality.test-samples';

import { PersonalityService } from './personality.service';

const requireRestSample: IPersonality = {
  ...sampleWithRequiredData,
};

describe('Personality Service', () => {
  let service: PersonalityService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonality | IPersonality[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonalityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Personality', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personality = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personality).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Personality', () => {
      const personality = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personality).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Personality', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Personality', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Personality', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonalityToCollectionIfMissing', () => {
      it('should add a Personality to an empty array', () => {
        const personality: IPersonality = sampleWithRequiredData;
        expectedResult = service.addPersonalityToCollectionIfMissing([], personality);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personality);
      });

      it('should not add a Personality to an array that contains it', () => {
        const personality: IPersonality = sampleWithRequiredData;
        const personalityCollection: IPersonality[] = [
          {
            ...personality,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonalityToCollectionIfMissing(personalityCollection, personality);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Personality to an array that doesn't contain it", () => {
        const personality: IPersonality = sampleWithRequiredData;
        const personalityCollection: IPersonality[] = [sampleWithPartialData];
        expectedResult = service.addPersonalityToCollectionIfMissing(personalityCollection, personality);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personality);
      });

      it('should add only unique Personality to an array', () => {
        const personalityArray: IPersonality[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personalityCollection: IPersonality[] = [sampleWithRequiredData];
        expectedResult = service.addPersonalityToCollectionIfMissing(personalityCollection, ...personalityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personality: IPersonality = sampleWithRequiredData;
        const personality2: IPersonality = sampleWithPartialData;
        expectedResult = service.addPersonalityToCollectionIfMissing([], personality, personality2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personality);
        expect(expectedResult).toContain(personality2);
      });

      it('should accept null and undefined values', () => {
        const personality: IPersonality = sampleWithRequiredData;
        expectedResult = service.addPersonalityToCollectionIfMissing([], null, personality, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personality);
      });

      it('should return initial array if no Personality is added', () => {
        const personalityCollection: IPersonality[] = [sampleWithRequiredData];
        expectedResult = service.addPersonalityToCollectionIfMissing(personalityCollection, undefined, null);
        expect(expectedResult).toEqual(personalityCollection);
      });
    });

    describe('comparePersonality', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonality(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonality(entity1, entity2);
        const compareResult2 = service.comparePersonality(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonality(entity1, entity2);
        const compareResult2 = service.comparePersonality(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonality(entity1, entity2);
        const compareResult2 = service.comparePersonality(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

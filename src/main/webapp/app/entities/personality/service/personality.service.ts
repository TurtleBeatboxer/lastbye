import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonality, NewPersonality } from '../personality.model';

export type PartialUpdatePersonality = Partial<IPersonality> & Pick<IPersonality, 'id'>;

export type EntityResponseType = HttpResponse<IPersonality>;
export type EntityArrayResponseType = HttpResponse<IPersonality[]>;

@Injectable({ providedIn: 'root' })
export class PersonalityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/personalities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personality: NewPersonality): Observable<EntityResponseType> {
    return this.http.post<IPersonality>(this.resourceUrl, personality, { observe: 'response' });
  }

  update(personality: IPersonality): Observable<EntityResponseType> {
    return this.http.put<IPersonality>(`${this.resourceUrl}/${this.getPersonalityIdentifier(personality)}`, personality, {
      observe: 'response',
    });
  }

  partialUpdate(personality: PartialUpdatePersonality): Observable<EntityResponseType> {
    return this.http.patch<IPersonality>(`${this.resourceUrl}/${this.getPersonalityIdentifier(personality)}`, personality, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonality>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonality[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonalityIdentifier(personality: Pick<IPersonality, 'id'>): number {
    return personality.id;
  }

  comparePersonality(o1: Pick<IPersonality, 'id'> | null, o2: Pick<IPersonality, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonalityIdentifier(o1) === this.getPersonalityIdentifier(o2) : o1 === o2;
  }

  addPersonalityToCollectionIfMissing<Type extends Pick<IPersonality, 'id'>>(
    personalityCollection: Type[],
    ...personalitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personalities: Type[] = personalitiesToCheck.filter(isPresent);
    if (personalities.length > 0) {
      const personalityCollectionIdentifiers = personalityCollection.map(
        personalityItem => this.getPersonalityIdentifier(personalityItem)!
      );
      const personalitiesToAdd = personalities.filter(personalityItem => {
        const personalityIdentifier = this.getPersonalityIdentifier(personalityItem);
        if (personalityCollectionIdentifiers.includes(personalityIdentifier)) {
          return false;
        }
        personalityCollectionIdentifiers.push(personalityIdentifier);
        return true;
      });
      return [...personalitiesToAdd, ...personalityCollection];
    }
    return personalityCollection;
  }
}

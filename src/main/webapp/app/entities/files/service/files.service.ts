import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFiles, NewFiles } from '../files.model';

export type PartialUpdateFiles = Partial<IFiles> & Pick<IFiles, 'id'>;

export type EntityResponseType = HttpResponse<IFiles>;
export type EntityArrayResponseType = HttpResponse<IFiles[]>;

@Injectable({ providedIn: 'root' })
export class FilesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/files');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(files: NewFiles): Observable<EntityResponseType> {
    return this.http.post<IFiles>(this.resourceUrl, files, { observe: 'response' });
  }

  update(files: IFiles): Observable<EntityResponseType> {
    return this.http.put<IFiles>(`${this.resourceUrl}/${this.getFilesIdentifier(files)}`, files, { observe: 'response' });
  }

  partialUpdate(files: PartialUpdateFiles): Observable<EntityResponseType> {
    return this.http.patch<IFiles>(`${this.resourceUrl}/${this.getFilesIdentifier(files)}`, files, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFiles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFiles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFilesIdentifier(files: Pick<IFiles, 'id'>): number {
    return files.id;
  }

  compareFiles(o1: Pick<IFiles, 'id'> | null, o2: Pick<IFiles, 'id'> | null): boolean {
    return o1 && o2 ? this.getFilesIdentifier(o1) === this.getFilesIdentifier(o2) : o1 === o2;
  }

  addFilesToCollectionIfMissing<Type extends Pick<IFiles, 'id'>>(
    filesCollection: Type[],
    ...filesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const files: Type[] = filesToCheck.filter(isPresent);
    if (files.length > 0) {
      const filesCollectionIdentifiers = filesCollection.map(filesItem => this.getFilesIdentifier(filesItem)!);
      const filesToAdd = files.filter(filesItem => {
        const filesIdentifier = this.getFilesIdentifier(filesItem);
        if (filesCollectionIdentifiers.includes(filesIdentifier)) {
          return false;
        }
        filesCollectionIdentifiers.push(filesIdentifier);
        return true;
      });
      return [...filesToAdd, ...filesCollection];
    }
    return filesCollection;
  }
}

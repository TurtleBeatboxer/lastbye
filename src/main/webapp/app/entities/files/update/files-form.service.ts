import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFiles, NewFiles } from '../files.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFiles for edit and NewFilesFormGroupInput for create.
 */
type FilesFormGroupInput = IFiles | PartialWithRequiredKeyOf<NewFiles>;

type FilesFormDefaults = Pick<NewFiles, 'id'>;

type FilesFormGroupContent = {
  id: FormControl<IFiles['id'] | NewFiles['id']>;
  name: FormControl<IFiles['name']>;
  type: FormControl<IFiles['type']>;
  format: FormControl<IFiles['format']>;
  filePath: FormControl<IFiles['filePath']>;
  profile: FormControl<IFiles['profile']>;
};

export type FilesFormGroup = FormGroup<FilesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FilesFormService {
  createFilesFormGroup(files: FilesFormGroupInput = { id: null }): FilesFormGroup {
    const filesRawValue = {
      ...this.getFormDefaults(),
      ...files,
    };
    return new FormGroup<FilesFormGroupContent>({
      id: new FormControl(
        { value: filesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(filesRawValue.name),
      type: new FormControl(filesRawValue.type),
      format: new FormControl(filesRawValue.format),
      filePath: new FormControl(filesRawValue.filePath),
      profile: new FormControl(filesRawValue.profile),
    });
  }

  getFiles(form: FilesFormGroup): IFiles | NewFiles {
    return form.getRawValue() as IFiles | NewFiles;
  }

  resetForm(form: FilesFormGroup, files: FilesFormGroupInput): void {
    const filesRawValue = { ...this.getFormDefaults(), ...files };
    form.reset(
      {
        ...filesRawValue,
        id: { value: filesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FilesFormDefaults {
    return {
      id: null,
    };
  }
}

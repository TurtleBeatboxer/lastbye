import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-recovery-question-form',
  templateUrl: './recovery-question-form.component.html',
  styleUrls: ['./recovery-question-form.component.css'],
})
export class RecoveryQuestionFormComponent implements OnInit {
  questionForm = new FormGroup({
    question: new FormControl('', { nonNullable: true }),
    questionAnswer: new FormControl('', { nonNullable: true }),
  });
  constructor(private router: Router, private http: HttpClient) {}
  submit() {
    console.log(this.questionForm.getRawValue());
    this.http.post('url', this.questionForm.getRawValue());
    this.router.navigate(['user/codeQr'], { skipLocationChange: true });
  }
  ngOnInit(): void {}
}

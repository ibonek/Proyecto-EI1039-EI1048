import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoordenadasFormComponent } from './coordenadas-form.component';

describe('UserFormComponent', () => {
  let component: CoordenadasFormComponent;
  let fixture: ComponentFixture<CoordenadasFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoordenadasFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoordenadasFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

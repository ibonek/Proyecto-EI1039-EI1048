import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoordenadasListComponent } from './coordenadas-list.component';

describe('UserListComponent', () => {
  let component: CoordenadasListComponent;
  let fixture: ComponentFixture<CoordenadasListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoordenadasListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoordenadasListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

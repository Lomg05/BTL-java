-- Câu 2
create or alter procedure procCau2
	@MaNV varchar(10),
	@Nam int,
	@SoLuongHD int output
as begin
	select @SoLuongHD = count(*)
	from HoaDonTT hd
	join PhieuDat pd on pd.MaBooking = hd.MaBooking
	where pb.MaNV = @MaNV and year(hd.NgayTT) = @Nam
end
go

declare @SoLuongHD int
exec procCau2 'KH001', 2025, @SoLuongHD output
select 'KH001' as MaVN, 2025 as Nam, @SoLuongHD as SoLuongHD
go


-- Câu 3
create function funcCau3 (@MaBooking varchar(10))
returns table
as return (
	select lp.MaBooking, lp.NgayDenDuKien, lp.NgayDiDuKien, lp.KieuPhong, p.SLPhong
	from MaBooking mb
	join mb p on p.Soluong = lp.SLPhong
	where lp.MaBooking = @MaBooking
)
go

select * from funcCau3('LP001')
go


-- Câu 4
alter table LoaiPhong
add SLPhongDat int default 0 with values
go

create or alter trigger triggerCau4 
on ChiTietPhongDat
for insert, update, delete
as begin
	set nocount on

	update pd
	set pd.SLPhongDat = isnull(sum(ct.SLPhong), 0)
	from PhieuDat pd
	join ChiTietPhongDat ct on ct.MaBooking = pd.MaBooking
	where pd.MaBooking in (
		select MaBooking from inserted
		union
		select MaBooking from deleted
	)
end
go


-- Câu 5
create or alter view viewCau5
as
	select nv.MaNV, nv.TenNV, 
		hd.MaHDTT, hd.NgayLapHD, hd.NgayTT, hd.PhuongThucTT, hd.MaBooking, 
		pd.NgayDenDuKien, pd.NgayDiDuKien
	from NhanVien nv
	join HoaDonTT hd on hd.MaNV = nv.MaNV
	join PhieuDat pd on pd.MaBooking = hd.MaBooking
	where pd.NgayDenDuKien between '2022-12-12' and '2022-12-19'
go

select * from viewCau5
go


package com.jbk.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.dao.ProductDao;
import com.jbk.entity.ProductEntity;
import com.jbk.model.CategoryModel;
import com.jbk.model.ProductModel;
import com.jbk.model.SupplierModel;
import com.jbk.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService 
{

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ProductDao productDao;
	
	@Override
	public int addProduct(ProductModel productModel) 
	{
		ProductEntity productEntity = modelMapper.map(productModel, ProductEntity.class);

		return productDao.addProduct(productEntity);
	}

	@Override
	public ProductModel getProductById(long productId) 
	{
		ProductEntity productEntity = productDao.getProductById(productId);
		
		if(productEntity!=null)
		{
			ProductModel productModel = modelMapper.map(productEntity, ProductModel.class);
			return productModel;
		}

		return null;
	}

	@Override
	public List<ProductModel> getAllProduct() 
	{
        List<ProductEntity> productEntitylist = productDao.getAllProduct();
		
		List<ProductModel> productModelList = new ArrayList<>();

	        for (ProductEntity ele : productEntitylist)
	        {
	        	ProductModel productModel = modelMapper.map(ele, ProductModel.class);
	        	productModelList.add(productModel);
	        }
	        return productModelList;
	}
	
	@Override
	public List<ProductModel> deleteProduct(long productId) 
	{
		List<ProductEntity> remainingProduct = productDao.deleteProduct(productId);
		
		List<ProductModel> productModelList = new ArrayList<>();
		
		if(remainingProduct!=null)
		{
			for (ProductEntity ele : remainingProduct)
	        {
				ProductModel productModel = modelMapper.map(ele, ProductModel.class);
				productModelList.add(productModel);
	        }
	        return productModelList;
		}
		
		return null;
	}

	@Override
	public ProductModel updateProduct(ProductModel productModel) 
	{
		ProductEntity productEntity = modelMapper.map(productModel, ProductEntity.class);

		ProductEntity updatedProductEntity = productDao.updateProduct(productEntity);
		
		if(updatedProductEntity!=null)
		{
			ProductModel updatedProductModel = modelMapper.map(updatedProductEntity, ProductModel.class);
			return updatedProductModel;
		}

		return null;
	}

	@Override
	public List<ProductModel> getAllProductsByOrder(String orderType, String propertyName) 
	{
		List<ProductEntity> allProductEntity = productDao.getAllProductsByOrder(orderType, propertyName);

		List<ProductModel> productModelList = new ArrayList<>();

		if(allProductEntity!=null && !allProductEntity.isEmpty())
		{
			for (ProductEntity ele : allProductEntity)
			{
				ProductModel productModel = modelMapper.map(ele, ProductModel.class);
				productModelList.add(productModel);
			}
			return productModelList;
		}
		return null;
	}


	@Override
	public List<ProductModel> getAllProductsHigherThanGivenPrice(Double productPrice) 
	{
		List<ProductEntity> list = productDao.getAllProductsHigherThanGivenPrice(productPrice);	

		List<ProductModel> productModelList = new ArrayList<>();

		if(!list.isEmpty())
		{
			for (ProductEntity ele : list)
			{
				ProductModel productModel = modelMapper.map(ele, ProductModel.class);
				productModelList.add(productModel);
			}
			return productModelList;
		}
		return null;
	}

	@Override
	public List<ProductModel> getAllProductsByStartingCharacterSequence(String searchCharacter) {
	
		List<ProductEntity> list = productDao.getAllProductsByStartingCharacterSequence(searchCharacter);
		List<ProductModel> productModelList = new ArrayList<>();

		if(!list.isEmpty())
		{
			for (ProductEntity ele : list)
			{
				ProductModel productModel = modelMapper.map(ele, ProductModel.class);
				productModelList.add(productModel);
			}
			return productModelList;
		}
		return null;
	}

	@Override
	public List<ProductModel> getAllProductsOfSpecifiedCategory(Long categoryId)
	{
		List<ProductEntity> list = productDao.getAllProductsOfSpecifiedCategory(categoryId);
		
		List<ProductModel> productModelList = new ArrayList<>();

		if(!list.isEmpty())
		{
			for (ProductEntity ele : list)
			{
				ProductModel productModel = modelMapper.map(ele, ProductModel.class);
				productModelList.add(productModel);
			}
			return productModelList;
		}
		return null;
	}
	
	@Override
	public ProductModel highestProductByPrice() 
	{
		  ProductEntity productEntity = productDao.highestProductByPrice();
		  
		  if(productEntity!=null)
			{
				ProductModel productModel = modelMapper.map(productEntity, ProductModel.class);
				return productModel;
			}
		return null;
	}

	@Override
	public double sumOfProductPrices() 
	{
		return productDao.sumOfProductPrices();
	}

	@Override
	public int getTotalCountOfProducts()
	{	
		return productDao.getTotalCountOfProducts();
	}

	@Override
	public ProductModel secondHighestProductByPrice() 
	{
		  ProductEntity productEntity = productDao.secondHighestProductByPrice();
		  
		  if(productEntity!=null)
			{
				ProductModel productModel = modelMapper.map(productEntity, ProductModel.class);
				return productModel;
			}
		return null;
	}
	
	@Override
	public Map<String, Object> uploadSheet(MultipartFile file) 
	{
		String fileName = file.getOriginalFilename();
		try
		{
			FileOutputStream fos=new FileOutputStream("src/main/resources/"+fileName);
			try 
			{
				byte[] data = file.getBytes();
				fos.write(data);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		// read excel data

				List<ProductModel> list = readExcel("src/main/resources/" + fileName);
				
				for (ProductModel ele : list) 
				{
					addProduct(ele);
				}
		
		return null;
	}

	private List<ProductModel> readExcel(String filePath) 
	{
		   List<ProductModel> list = new ArrayList<>();
	        try
	        {
				Workbook workbook=new XSSFWorkbook(filePath);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				while (rows.hasNext()) 
				{
					Row row = rows.next();
					int rowNum = row.getRowNum();
					if(rowNum==0)
					{
						continue;
					}
					
					ProductModel productModel=new ProductModel();
					
					String productId = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
					productModel.setProductId(Long.parseLong(productId) + rowNum);

					
					Iterator<Cell> cells = row.cellIterator();
					
					while (cells.hasNext()) 
					{
						Cell cell = cells.next();
						
						int columnIndex = cell.getColumnIndex();
						
						switch (columnIndex) 
						{
						case 0:
							productModel.setProductName(cell.getStringCellValue());
							break;

                        case 1:
							SupplierModel supplierModel=new SupplierModel();
							supplierModel.setSupplierId((long)cell.getNumericCellValue());
							productModel.setSupplier(supplierModel);
							break;
							
                         case 2:
                        	 CategoryModel categoryModel = new CategoryModel();
                        	 categoryModel.setCategoryId((long) cell.getNumericCellValue());
                        	 productModel.setCategory(categoryModel);
     						break;
							
                         case 3:
                        	 productModel.setProductQty((int) cell.getNumericCellValue());
     						break;

     					case 4:
     						productModel.setProductPrice(cell.getNumericCellValue());
     						break;
						}
						
					}
					
					 list.add(productModel);					
				}
			}

	        catch (IOException e) 
	        {				
				e.printStackTrace();
			}
	        return list;
	}
	
}

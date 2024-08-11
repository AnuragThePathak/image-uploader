export default function UploadedImages({images}: {images: string[]}) {
	return (
		<div className="flex space-x-2 overflow-x-auto whitespace-nowrap ">
			{images.map((image, index) => (
				<img key={index} src={image} alt={`image-${index}`}
					className="h-44 inline-block" />
			))}
		</div>
	)
}
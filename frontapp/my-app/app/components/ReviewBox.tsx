import { ReviewInterface } from "../interface/review/ReviewInterfaces";
interface ReviewBoxProps {
  review: ReviewInterface;
  formatDate: (dateString: string) => string;
}

const ReviewBox: React.FC<ReviewBoxProps> = ({ review, formatDate }) => {
  return (
    <section
      key={review.id}
      className="text-gray-600 body-font overflow-hidden mx-auto"
    >
      <div className="container px-5 py-12 mx-auto">
        <div className="-my-8 divide-y-2 divide-gray-100">
          <div className="py-8 flex flex-wrap md:flex-nowrap">
            <div className="md:w-64 md:mb-0 mb-6 flex-shrink-0 flex flex-col">
              <span className="font-semibold title-font text-gray-700">
                {review.author.username}
              </span>
              <span className="mt-1 text-gray-500 text-sm">
                {formatDate(review.createdDate)}
              </span>
            </div>
            <div className="md:flex-grow">
              <p className="leading-relaxed">{review.content}</p>
              <a className="text-green-500 inline-flex items-center mt-4">
                !수정, 삭제 버튼 자리
                <svg
                  className="w-4 h-4 ml-2"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  strokeWidth={2}
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path d="M5 12h14" />
                  <path d="M12 5l7 7-7 7" />
                </svg>
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ReviewBox;
